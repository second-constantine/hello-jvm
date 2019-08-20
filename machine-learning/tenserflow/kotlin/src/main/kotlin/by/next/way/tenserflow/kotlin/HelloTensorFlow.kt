package by.next.way.tenserflow.kotlin

import org.tensorflow.Graph
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.TensorFlow


object HelloTensorFlow {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        Graph().use { g ->
            val value = "Hello from " + TensorFlow.version()

            // Construct the computation graph with a single operation, a constant
            // named "MyConst" with a value "value".
            Tensor.create(value.toByteArray(charset("UTF-8"))).use { t ->
                // The Java API doesn't yet include convenience functions for adding operations.
                g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType()).setAttr("value", t).build()
            }

            // Execute the "MyConst" operation in a Session.
            Session(g).use { s ->
                s.runner().fetch("MyConst").run()[0]
                        .use { output -> println(String(output.bytesValue(), charset("UTF-8"))) }
            }// Generally, there may be multiple output tensors,
            // all of them must be closed to prevent resource leaks.
        }
    }
}
