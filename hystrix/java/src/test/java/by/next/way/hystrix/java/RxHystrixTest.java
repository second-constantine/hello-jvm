package by.next.way.hystrix.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static by.next.way.hystrix.java.hystrix.RxHystrix.*;

public class RxHystrixTest {

    private static final Logger log = LoggerFactory.getLogger(RxHystrixTest.class);

    @AfterEach
    public void after() throws InterruptedException {
        Thread.sleep(300);
    }


    /**
     * нативный тест, все работает
     */
    @Test
    public void testNaive() {
        List<Integer> source = IntStream.range(1, 7).boxed().collect(Collectors.toList());

        Observable<String> observable = Observable.from(source)
                .flatMap(elem -> executeCommand(elem.toString()));

        toList(observable).forEach(el -> log.info("List element: {}", el));
    }

    /**
     * увеличиваем размер списка и внезапно падаем
     * @throws Exception HystrixRuntimeException от переполнения пула
     */
    @Test
    public void testStupid() throws Exception {
        try {
            List<Integer> source = IntStream.range(1, 50).boxed().collect(Collectors.toList());

            Observable<String> observable = Observable.from(source)
                    .flatMap(elem -> executeCommandDelayed(elem.toString()));

            toList(observable).forEach(el -> log.info("List element: {}", el));
            Assertions.assertTrue(false);
        } catch (Exception ex) {
            log.error("Ooops", ex);
            Assertions.assertTrue(true);
        }
    }

    /**
     * разбиваем поток на ленивые команды, формируем пачки по 7 штук, которые последовательно перебираются,
     * но сами элементы пачки обрабатываются параллельно
     */
    @Test
    public void testWindow() {
        List<Integer> source = IntStream.range(1, 50).boxed().collect(Collectors.toList());

        Observable<String> observable =  Observable.from(source)
                        .map(elem -> executeCommandDelayed(elem.toString()))
                        .window(7)
                        .concatMap(window -> window.flatMap(x -> x));

        toList(observable).forEach(el -> log.info("List element: {}", el));
    }
}