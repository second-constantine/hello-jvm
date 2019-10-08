package by.next.way.neural.network.easy

import java.io.Serializable


data class TrainingSet(
        val name: String = "",
        val data: MutableList<MutableList<MutableList<Double>>>
) : Serializable