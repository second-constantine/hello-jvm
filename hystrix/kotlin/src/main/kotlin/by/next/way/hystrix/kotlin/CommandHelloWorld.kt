package by.next.way.hystrix.kotlin

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey


internal class CommandHelloWorld(
        private val name: String
) : HystrixCommand<String>(
        HystrixCommandGroupKey.Factory.asKey("ExampleGroup")
) {

    override fun run(): String {
        return "Hello $name!"
    }
}