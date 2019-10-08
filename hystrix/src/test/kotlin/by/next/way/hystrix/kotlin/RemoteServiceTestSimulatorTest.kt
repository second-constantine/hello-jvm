package by.next.way.hystrix.kotlin

import by.next.way.hystrix.kotlin.RemoteServiceTestSimulator.Companion.invokeRemoteService
import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandProperties
import com.netflix.hystrix.HystrixThreadPoolProperties
import com.netflix.hystrix.exception.HystrixRuntimeException
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class RemoteServiceTestSimulatorTest {

    @Test
    @Throws(InterruptedException::class)
    fun givenSvcTimeoutOf100AndDefaultSettings_whenRemoteSvcExecuted_thenReturnSuccess() {
        val config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroup2"))
        val result = RemoteServiceTestCommand(config, RemoteServiceTestSimulator(100)).execute()
        log.info(result)
        Assertions.assertEquals(result, "Success")
    }

    @Test
    @Throws(InterruptedException::class)
    fun givenSvcTimeoutOf5000AndExecTimeoutOf10000_whenRemoteSvcExecuted_thenReturnSuccess() {

        val config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroupTest4"))

        val commandProperties = HystrixCommandProperties.Setter()
        commandProperties.withExecutionTimeoutInMilliseconds(10000)
        config.andCommandPropertiesDefaults(commandProperties)

        val result = RemoteServiceTestCommand(config, RemoteServiceTestSimulator(500)).execute()
        log.info(result)
        Assertions.assertEquals(result, "Success")
    }

    @Test
    @Throws(InterruptedException::class)
    fun givenSvcTimeoutOf15000AndExecTimeoutOf5000_whenRemoteSvcExecuted_thenExpectHre() {

        val config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroupTest5"))

        val commandProperties = HystrixCommandProperties.Setter()
        commandProperties.withExecutionTimeoutInMilliseconds(5000)
        config.andCommandPropertiesDefaults(commandProperties)

        try {
            RemoteServiceTestCommand(config, RemoteServiceTestSimulator(15000)).execute()
            Assertions.assertTrue(false)
        } catch (hre: HystrixRuntimeException) {
            Assertions.assertTrue(true)
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun givenSvcTimeoutOf500AndExecTimeoutOf10000AndThreadPool_whenRemoteSvcExecuted_thenReturnSuccess() {

        val config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroupThreadPool"))

        val commandProperties = HystrixCommandProperties.Setter()
        commandProperties.withExecutionTimeoutInMilliseconds(10000)
        config.andCommandPropertiesDefaults(commandProperties)
        config.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withMaxQueueSize(10)
                .withCoreSize(3)
                .withQueueSizeRejectionThreshold(10))
        val result = RemoteServiceTestCommand(config, RemoteServiceTestSimulator(500)).execute()
        log.info(result)
        Assertions.assertEquals(result, "Success")
    }

    @Test
    @Throws(InterruptedException::class)
    fun givenCircuitBreakerSetup_whenRemoteSvcCmdExecuted_thenReturnSuccess() {

        val config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceGroupCircuitBreaker"))

        val properties = HystrixCommandProperties.Setter()
        properties.withExecutionTimeoutInMilliseconds(1000)
        properties.withCircuitBreakerSleepWindowInMilliseconds(4000)
        properties.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
        properties.withCircuitBreakerEnabled(true)
        properties.withCircuitBreakerRequestVolumeThreshold(1)

        config.andCommandPropertiesDefaults(properties)
        config.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withMaxQueueSize(1)
                .withCoreSize(1)
                .withQueueSizeRejectionThreshold(1))

        Assertions.assertNull(invokeRemoteService(config, 10_000))
        Assertions.assertNull(invokeRemoteService(config, 10_000))
        Assertions.assertNull(invokeRemoteService(config, 10_000))

        Thread.sleep(5000)
        var result = RemoteServiceTestCommand(config, RemoteServiceTestSimulator(500)).execute()
        log.info(result)
        Assertions.assertEquals(result, "Success")
        result = RemoteServiceTestCommand(config, RemoteServiceTestSimulator(500)).execute()
        log.info(result)
        Assertions.assertEquals(result, "Success")
        result = RemoteServiceTestCommand(config, RemoteServiceTestSimulator(500)).execute()
        log.info(result)
        Assertions.assertEquals(result, "Success")
    }

    companion object {
        private val log = LogManager.getLogger()
    }
}