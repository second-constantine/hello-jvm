package by.next.way.feign.kotlin

import feign.Feign
import feign.Logger
import feign.RequestLine
import feign.gson.GsonDecoder

interface FeignConnector {

    @RequestLine("POST /post")
    fun post(raw: String): String

    companion object {

        fun connect(connectionURL: String): FeignConnector {
            val decoder = GsonDecoder()
            return Feign.builder()
                    .errorDecoder(MyErrorDecoder(decoder))
                    .logger(Logger.ErrorLogger())
                    .logLevel(Logger.Level.BASIC)
                    .target(FeignConnector::class.java, connectionURL)
        }
    }

}