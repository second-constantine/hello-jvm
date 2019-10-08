package by.next.way.neural.network.app

import by.next.way.neural.network.easy.NeuralNetwork
import by.next.way.neural.network.easy.TrainingSet
import com.google.gson.Gson
import com.sun.net.httpserver.HttpServer
import org.apache.logging.log4j.LogManager
import java.io.*
import java.net.InetSocketAddress
import java.util.*


object NeuralNetworkWebImageApp {

    private val log = LogManager.getLogger()

    @JvmStatic
    fun main(args: Array<String>) {
        val port = if (args.isNotEmpty()) args[0].toInt() else 1234
        log.info("Start web ML app on http://localhost:$port/")
        HttpServer.create(InetSocketAddress(port), 0).apply {
            createContext("/") { http ->
                http.responseHeaders.add("Content-type", "text/plain")
                http.responseHeaders.add("Access-Control-Allow-Origin", "*")
                http.sendResponseHeaders(200, 0)
                PrintWriter(http.responseBody).use { out ->
                    val headerData = http.requestURI.toString()
                    if (headerData.isNotBlank()) {
                        log.info("header -> $headerData")
                        saveImage(headerData)
                        out.print("7")
                    }
                    val bodyData = http.requestBody.readBytes().toString(charset("utf-8"))
                    if (bodyData.isNotBlank()) {
                        out.println(bodyData)
                        log.info("body -> $bodyData")
                    }
                }
            }
            start()
        }
    }

    fun trainML() {
        val neuralNetwork = NeuralNetwork(2, 1)
        val trainingSet = Gson().fromJson("""
{
    data: [
    [[0,0],[0]],
    [[0,1],[1]],
    [[1,0],[1]],
    [[1,1],[0]]
    ]
}
        """, TrainingSet::class.java)
        neuralNetwork.train(trainingSet.data)
    }

    fun saveML(neuralNetwork: NeuralNetwork, name: String = "webImageML.ml") {
        ObjectOutputStream(FileOutputStream(File(name)))
                .use { it.writeObject(neuralNetwork) }
    }

    fun loadML(name: String = "webImageML.ml") = ObjectInputStream(FileInputStream(File(name)))
            .use { it.readObject() } as NeuralNetwork

    fun saveImage(base64: String) {
        try {
            val base64Image = base64.split(",")[1]
            val imageBytes = Base64.getDecoder().decode(base64Image)
            val file = File("image.png")
            BufferedOutputStream(FileOutputStream(file)).use { outputStream -> outputStream.write(imageBytes) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}