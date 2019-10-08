package by.next.way.neural.network.easy

import java.io.Serializable
import kotlin.math.exp
import kotlin.math.pow

data class Neuron(
        var output: Double = 0.0,
        var inputs: MutableList<Double> = mutableListOf(),
        var weights: MutableList<Double> = mutableListOf()
) : Serializable {

    fun calculateOutput(inputs: MutableList<Double>): Double {
        var total = 0.0
        for (i in inputs.indices) {
            total += inputs[i] * weights[i]
        }
        this.inputs = inputs
        this.output = 1 / (1 + exp(-total))
        return output
    }

    fun calculateError(targetOutput: Double) = 0.5 * (targetOutput - output).pow(2.0)

    fun calculateErrorInput(targetOutput: Double) = -(targetOutput - output) * calculate()

    fun calculate() = output * (1 - output)

}
