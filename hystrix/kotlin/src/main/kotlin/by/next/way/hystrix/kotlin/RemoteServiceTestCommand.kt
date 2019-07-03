package by.next.way.hystrix.kotlin

import com.netflix.hystrix.HystrixCommand


internal class RemoteServiceTestCommand(
        config: HystrixCommand.Setter,
        private val remoteService: RemoteServiceTestSimulator
) : HystrixCommand<String>(config) {

    @Throws(Exception::class)
    override fun run(): String {
        return remoteService.execute()
    }
}