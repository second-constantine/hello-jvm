package by.next.way.threads.example.phaser

import java.util.concurrent.Phaser

fun main() {
    val start = Phaser(8)
    for (i in 1..5) {
        Thread(Car(
                start = start,
                carNumber = i,
                carSpeed = (Math.random() * 100 + 50).toInt()
        )).start()
        Thread.sleep(100)
    }

    while (start.registeredParties > 3) {
        //Проверяем, собрались ли все автомобили
        Thread.sleep(100)                  //у стартовой прямой. Если нет, ждем 100ms
    }

    Thread.sleep(100)
    println("На старт!")
    start.arriveAndDeregister()
    Thread.sleep(100)
    println("Внимание!")
    start.arriveAndDeregister()
    Thread.sleep(100)
    println("Марш!")
    start.arriveAndDeregister()
}

class Car(
        private val start: Phaser,
        private val carNumber: Int,
        private val carSpeed: Int
) : Runnable {

    override fun run() {
        try {
            System.out.printf("Автомобиль №%d подъехал к стартовой прямой.\n", carNumber)
            start.arriveAndDeregister()
            start.awaitAdvance(0)
            Thread.sleep((TRACK_LENGTH / carSpeed).toLong())
            System.out.printf("Автомобиль №%d финишировал!\n", carNumber)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    companion object {
        const val TRACK_LENGTH = 500000
    }
}