package by.next.way.hystrix.java.hystrix;

/**
 * Интерфейс для вызова хистриксной команды, аналог Function
 * @param <T> element
 * @param <Ret> return
 */
public interface HystrixBasedMethod<T, Ret> {
    /**
     * convert one element by hystrix command
     * @param elem elem
     * @return map result
     */
    Ret hystrixCommand(T elem);
}
