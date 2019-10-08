package by.next.way.hystrix.kotlin

import com.netflix.hystrix.exception.HystrixRuntimeException
import com.netflix.hystrix.HystrixCommand


class RemoteServiceTestSimulator(
        private val wait: Long
) {

    @Throws(InterruptedException::class)
    fun execute(): String {
        Thread.sleep(wait)
        return "Success"
    }
    companion object {
        @Throws(InterruptedException::class)
        fun invokeRemoteService(config: HystrixCommand.Setter, timeout: Long): String? {

            var response: String? = null

            try {
                response = RemoteServiceTestCommand(config,
                        RemoteServiceTestSimulator(timeout)).execute()
            } catch (ex: HystrixRuntimeException) {
                println("ex = $ex")
            }

            return response
        }
    }
}