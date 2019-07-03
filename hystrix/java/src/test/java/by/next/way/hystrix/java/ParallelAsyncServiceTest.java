package by.next.way.hystrix.java;

import by.next.way.hystrix.java.stream.CommandHelloWorld;
import by.next.way.hystrix.java.stream.ParallelAsyncService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelAsyncServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ParallelAsyncServiceTest.class);

    @AfterEach
    public void after() throws InterruptedException {
        Thread.sleep(300);
    }

    /**
     * работает но очень медленно, вызовы последовательны, 1238ms
     */
    @Test
    public void testNaive() {
        IntStream.range(1, 5).boxed().map(
                value -> executeCommand(value.toString())
        ).collect(Collectors.toList())
                .forEach(el -> log.info("List element: {}", el));
    }

    /**
     * падает - такое количество не влазит во внутрь
     */
    @Test
    public void testStupid() {

        try {
            IntStream.range(1, 50).boxed().map(
                    value -> executeCommandDelayed(value.toString())
            ).collect(Collectors.toList())
                    .forEach(el -> log.info("List element (FUTURE): {}", el.toString()));
            Assertions.assertTrue(false);
        } catch (Exception ex) {
            log.error("Ooops", ex);
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void testSmart() {
        ParallelAsyncService service = new ParallelAsyncService(7);
        service.waitStream(
                IntStream.range(1, 50).boxed().map(
                        service.parallelWarp(
                                value -> executeCommand(value.toString())
                        )
                )
        ).collect(Collectors.toList())
                .forEach(el -> log.info("List element: {}", el));
    }


    static String executeCommand(String str) {
        log.info("Direct Hystrix command created: {}", str);
        return new CommandHelloWorld(str).execute();
    }

    static Future<String> executeCommandDelayed(String str) {
        log.info("Direct Hystrix command created: {}", str);
        return new CommandHelloWorld(str).queue();
    }

}