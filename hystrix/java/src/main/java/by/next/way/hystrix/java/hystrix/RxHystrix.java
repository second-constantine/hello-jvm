package by.next.way.hystrix.java.hystrix;

import by.next.way.hystrix.java.stream.CommandHelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxHystrix {

    private static final Logger LOG = LoggerFactory.getLogger(RxHystrix.class);

    /**
     * создает список из observable, считая, что при отсутствии ошибок он будет порождать элемент не реже секунды
     * @param observable observable
     * @return список
     */
    public static List<String> toList(Observable<String> observable) {
        return observable.timeout(1, TimeUnit.SECONDS).toList().toBlocking().single();
    }

    /**
     * создает горячую хистриксную команду
     * @param str  входные данные
     * @return hot observable результата
     */
    public static Observable<String> executeCommand(String str) {
        LOG.info("Hot Hystrix command created: {}", str);
        return new CommandHelloWorld(str).observe();
    }

    /**
     * создает холодную хистриксную команду
     * @param str  входные данные
     * @return hot observable результата
     */
    public static Observable<String> executeCommandDelayed(String str) {
        LOG.info("Cold Hystrix command created: {}", str);
        return new CommandHelloWorld(str).toObservable();
    }
}
