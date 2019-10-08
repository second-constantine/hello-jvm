package by.next.way.neural.network.easy

import org.apache.logging.log4j.LogManager
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

data class NeuralNetwork(
        val numInputs: Int,
        val numOutputs: Int,
        val additionalHiddenLayers: Int = 0,
        private val key: String = "unknown",
        var dataSetProcessed: Long = 0,
        val layers: List<NeuronLayer> = generateLayers(
                numInputs = numInputs,
                numOutputs = numOutputs,
                hiddenLayerCount = additionalHiddenLayers
        )
) : Serializable {

    fun getKey() = "$key.ml"

    fun prediction(inputs: MutableList<Double>) = feedForward(inputs)[0]

    fun predictionStr(inputs: MutableList<Double>) = String.format("%.2f", feedForward(inputs)[0] * 100) + "%"

    fun feedForward(inputs: MutableList<Double>): MutableList<Double> {
        var result = inputs
        for (layer in layers.reversed()) {
            result = layer.feedForward(result)
        }
        return result
    }

    fun train(dataSet: MutableList<MutableList<MutableList<Double>>>, epochSize: Int = 100000) {
        val dataSetSize = dataSet.size
        for (i in 0 until epochSize) {
            log.info("[$key] epoch: $i from $epochSize | dataSetProcessed=$dataSetProcessed")
            for (choice in 0 until dataSetSize) {
                train(dataSet[choice][0], dataSet[choice][1])
            }
            dataSetProcessed += dataSetSize
        }
    }

    fun train(trainingInputs: MutableList<Double>, trainingOutputs: MutableList<Double>) {
        feedForward(trainingInputs)
        var outputLayer = layers[0]
        var errors = calculateInputErrorForOutputLayer(
                layer = outputLayer,
                trainingOutputs = trainingOutputs
        )
        updateNeuronsWeights(outputLayer, errors)
        for (layerIdx in 1 until layers.size) {
            val hiddenLayer = layers[layerIdx]
            errors = calculateInputErrorForHiddenLayer(
                    outputLayer = outputLayer,
                    layer = hiddenLayer,
                    errorInputForOutputLayer = errors
            )
            updateNeuronsWeights(hiddenLayer, errors)
            outputLayer = hiddenLayer
        }
    }

    private fun calculateInputErrorForOutputLayer(
            layer: NeuronLayer,
            trainingOutputs: List<Double>
    ): List<Double> {
        val errors = ArrayList<Double>()
        for (neuron in layer.neurons) {
            val outputIdx = layer.neurons.indexOf(neuron)
            val error = -(trainingOutputs[outputIdx] - neuron.output) * neuron.calculate()
            errors.add(error)
        }
        return errors
    }

    private fun calculateInputErrorForHiddenLayer(
            outputLayer: NeuronLayer,
            layer: NeuronLayer,
            errorInputForOutputLayer: List<Double>
    ): List<Double> {
        val errors = ArrayList<Double>()
        for (neuron in layer.neurons) {
            val idx = layer.neurons.indexOf(neuron)
            var error = 0.0
            for (outputNeuron in outputLayer.neurons) {
                val outputIdx = outputLayer.neurons.indexOf(outputNeuron)
                error += errorInputForOutputLayer[outputIdx] * outputNeuron.weights[idx]
            }
            errors.add(error * neuron.calculate())
        }
        return errors
    }

    private fun updateNeuronsWeights(layer: NeuronLayer, error: List<Double>) {
        for (neuron in layer.neurons) {
            val idx = layer.neurons.indexOf(neuron)
            for (w in 0 until neuron.weights.size) {
                var weight = neuron.weights[w]
                weight -= LEARNING_RATE * (error[idx] * neuron.inputs[w])
                neuron.weights[w] = weight
            }
        }
    }

    fun calculateError(trainingSets: List<List<MutableList<Double>>>): BigDecimal {
        val outputLayer = layers[0]
        var totalError = 0.0
        for (a in trainingSets.indices) {
            val trainingInputs = trainingSets[a][0]
            val trainingOutputs = trainingSets[a][1]
            feedForward(trainingInputs)
            for (i in trainingOutputs.indices) {
                totalError += outputLayer.neurons[i].calculateError(trainingOutputs[i])
            }
        }
        return BigDecimal.valueOf(totalError)
    }

    companion object {
        private val log = LogManager.getLogger()
        private const val LEARNING_RATE = 0.5
        private val random = Random()

        @JvmStatic
        fun generateLayers(numInputs: Int, numOutputs: Int, hiddenLayerCount: Int = 0): List<NeuronLayer> {
            val layers = arrayListOf(NeuronLayer.create(numOutputs))
            val layerSize = numInputs * numOutputs * 2
            for (i in 0..hiddenLayerCount) {
                layers.add(createLayers(layers, layerSize, layerSize))
            }
            layers.add(createLayers(layers, layerSize, numInputs))
            return layers
        }

        private fun createLayers(layers: List<NeuronLayer>, layerSize: Int, numInputs: Int): NeuronLayer {
            val hiddenLayer = NeuronLayer.create(layerSize)
            val outputLayer = layers.last()
            if (outputLayer.neurons[0].weights.size == 0) {
                for (outputNeuron in outputLayer.neurons) {
                    for (hiddenNeuron in hiddenLayer.neurons) {
                        outputNeuron.weights.add(random.nextDouble())
                    }
                }
            }

            for (hiddenNeuron in hiddenLayer.neurons) {
                for (i in 0 until numInputs) {
                    hiddenNeuron.weights.add(random.nextDouble())
                }
            }
            return hiddenLayer
        }

    }
}
