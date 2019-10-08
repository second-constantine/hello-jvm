package by.next.way.feign.kotlin

import feign.Response
import feign.codec.Decoder
import feign.codec.ErrorDecoder
import java.io.IOException

class MyErrorDecoder internal constructor(internal val decoder: Decoder) : ErrorDecoder {
    internal val defaultDecoder: ErrorDecoder = ErrorDecoder.Default()

    override fun decode(methodKey: String, response: Response): Exception {
        try {
            return decoder.decode(response, ClientError::class.java) as Exception
        } catch (fallbackToDefault: IOException) {
            return defaultDecoder.decode(methodKey, response)
        }

    }
}