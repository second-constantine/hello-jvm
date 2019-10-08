package by.next.way.neural.network

import by.next.way.neural.network.easy.ComplexML
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ComplexMLTest {


    @Test
    fun xor() {
        val complexML = ComplexML()
        complexML.fromJson("""{
    name: "XOR",
    data: [
    [[0,0],[0]],
    [[0,1],[1]],
    [[1,0],[1]],
    [[1,1],[0]]
    ]
}
        """, 10000)
        val zeroAndZero = complexML.predictionFirst(arrayListOf(0.0, 0.0))
        log.info(zeroAndZero)
        Assertions.assertTrue(zeroAndZero < 0.1)
        val oneAndZero = complexML.predictionFirst(arrayListOf(1.0, 0.0))
        log.info(oneAndZero)
        Assertions.assertTrue(oneAndZero > 0.9)
        val zeroAndOne = complexML.predictionFirst(arrayListOf(0.0, 1.0))
        log.info(zeroAndOne)
        Assertions.assertTrue(zeroAndOne > 0.9)
        val oneAndOne = complexML.predictionFirst(arrayListOf(1.0, 1.0))
        log.info(oneAndOne)
        Assertions.assertTrue(oneAndOne < 0.1)
    }


    companion object {
        private val log = LogManager.getLogger()
    }
}