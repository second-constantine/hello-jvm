package by.next.way.neural.network

import by.next.way.neural.network.easy.NeuralNetwork
import by.next.way.neural.network.easy.TrainingSet
import com.google.gson.Gson
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NeuronNetworkSimpleTest {

    private val gson = Gson()

    @Test
    fun xor() {
        val json = """{
    name: "XOR",
    data: [
    [[0,0],[0]],
    [[0,1],[1]],
    [[1,0],[1]],
    [[1,1],[0]]
    ]
}
        """
        val trainingSet = gson.fromJson(json, TrainingSet::class.java)
        val data = trainingSet.data
        val neuralNetwork = NeuralNetwork(
                key = trainingSet.name,
                numInputs = data[0][0].size,
                numOutputs = data[0][1].size
        )
        neuralNetwork.train(data, epochSize = 10000)
        val zeroAndZero = neuralNetwork.prediction(arrayListOf(0.0, 0.0))
        log.info(zeroAndZero)
        Assertions.assertTrue(zeroAndZero < 0.1)
        val oneAndZero = neuralNetwork.prediction(arrayListOf(1.0, 0.0))
        log.info(oneAndZero)
        Assertions.assertTrue(oneAndZero > 0.9)
        val zeroAndOne = neuralNetwork.prediction(arrayListOf(0.0, 1.0))
        log.info(zeroAndOne)
        Assertions.assertTrue(zeroAndOne > 0.9)
        val oneAndOne = neuralNetwork.prediction(arrayListOf(1.0, 1.0))
        log.info(oneAndOne)
        Assertions.assertTrue(oneAndOne < 0.1)
    }

    companion object {
        private val log = LogManager.getLogger()
    }
}