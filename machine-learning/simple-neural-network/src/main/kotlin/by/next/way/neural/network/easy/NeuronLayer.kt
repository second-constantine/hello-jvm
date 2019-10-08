package by.next.way.neural.network.easy

import java.io.Serializable
import java.util.*

data class NeuronLayer(
        val neurons: List<Neuron> = ArrayList()
) : Serializable {

    fun feedForward(inputs: MutableList<Double>): MutableList<Double> {
        val ouputs = mutableListOf<Double>()
        for (neuron in neurons) {
            ouputs.add(neuron.calculateOutput(inputs))
        }
        return ouputs
    }

    companion object {
        fun create(neuronAmount: Int): NeuronLayer {
            val neurons = arrayListOf<Neuron>()
            for (i in 0 until neuronAmount) {
                neurons.add(Neuron())
            }
            return NeuronLayer(
                    neurons = neurons
            )
        }
    }
}
