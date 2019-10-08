package by.next.way.neural.network.easy

import com.google.gson.Gson
import org.apache.logging.log4j.LogManager
import java.io.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

data class ComplexML(
        val neuralNetworks: HashMap<String, NeuralNetwork> = hashMapOf(),
        val gson: Gson = Gson(),
        val executors: ExecutorService = Executors.newFixedThreadPool(10)
) {

    fun fromJson(json: String, epochSize: Int = DEFAULT_EPOCH_SIZE): NeuralNetwork {
        val trainingSet = gson.fromJson(json, TrainingSet::class.java)
        val data = trainingSet.data
        val neuralNetwork = addNeuralNetwork(NeuralNetwork(
                key = trainingSet.name,
                numInputs = data[0][0].size,
                numOutputs = data[0][1].size,
                additionalHiddenLayers = 0
        ))
        train(data, epochSize)
        return neuralNetwork
    }

    fun loadFromFiles(fileNames: List<String>): ComplexML {
        for (fileName in fileNames) {
            val nn = loadML(fileName)
            if (nn != null) {
                neuralNetworks[fileName] = nn
                log.info("Neural network ${nn.getKey()} loaded")
            } else {
                log.info("Error loaded [$fileName]")
            }
        }
        return this
    }

    fun addNeuralNetwork(neuralNetwork: NeuralNetwork): NeuralNetwork {
        val key = neuralNetwork.getKey()
        val nn = loadML(key)
        return if (nn == null
                || (nn.layers.first().neurons.size != neuralNetwork.layers.first().neurons.size
                        && nn.layers.last().neurons.size != neuralNetwork.layers.last().neurons.size)) {
            neuralNetworks[key] = neuralNetwork
            log.info("Neural network $key created")
            neuralNetwork
        } else {
            neuralNetworks[key] = nn
            log.info("Neural network ${nn.getKey()} loaded")
            nn
        }
    }

    fun train(data: MutableList<MutableList<MutableList<Double>>>, epochSize: Int = DEFAULT_EPOCH_SIZE) {
        val downLatch = CountDownLatch(neuralNetworks.size)
        for (entity in neuralNetworks) {
            val neuralNetwork = entity.value
            executors.submit {
                neuralNetwork.train(data, epochSize = epochSize)
                saveML(neuralNetwork)
                downLatch.countDown()
            }
        }
        downLatch.await()
    }

    fun prediction(inputs: MutableList<Double>): List<Double> {
        val result = arrayListOf<Double>()
        for (entity in neuralNetworks) {
            val neuralNetwork = entity.value
            result.add(neuralNetwork.prediction(inputs))
        }
        return result
    }

    fun predictionFirst(inputs: MutableList<Double>) = prediction(inputs).first()

    private fun saveML(neuralNetwork: NeuralNetwork, name: String = neuralNetwork.getKey()) {
        ObjectOutputStream(FileOutputStream(File(name)))
                .use { it.writeObject(neuralNetwork) }
    }

    private fun loadML(name: String = "webImageML.ml") = try {
        ObjectInputStream(FileInputStream(File(name)))
                .use { it.readObject() } as NeuralNetwork
    } catch (e: FileNotFoundException) {
        null
    }

    companion object {
        private val log = LogManager.getLogger()
        private const val DEFAULT_EPOCH_SIZE = 100
    }
}