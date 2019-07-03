package by.next.way.hystrix.java.stream;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

public class CommandHelloWorld extends HystrixCommand<String> {
    private final String name;
    private final SecureRandom random = new SecureRandom();
    private static final Logger LOG = LoggerFactory.getLogger(CommandHelloWorld.class);

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws InterruptedException {
        LOG.info("Command start: {}", name);
        Thread.sleep(random.nextInt(500));
        LOG.info("Command calculation finished: {}", name);
        return "Hello " + name + "!";
    }
}
