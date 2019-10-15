package by.next.way.threads.example.cyclic.barrier

import java.util.concurrent.CyclicBarrier

fun main() {
    val barrier = CyclicBarrier(3, FerryBoat())
    for (i in 0..8) {
        Thread(Car(
                barrier = barrier,
                carNumber = i
        )).start()
        Thread.sleep(400)
    }
}

//Таск, который будет выполняться при достижении сторонами барьера
class FerryBoat : Runnable {
    override fun run() {
        try {
            Thread.sleep(500)
            println("Паром переправил автомобили!")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}

//Стороны, которые будут достигать барьера
class Car(
        private val barrier: CyclicBarrier,
        private val carNumber: Int
) : Runnable {

    override fun run() {
        try {
            System.out.printf("Автомобиль №%d подъехал к паромной переправе.\n", carNumber)
            //Для указания потоку о том что он достиг барьера, нужно вызвать метод await()
            //После этого данный поток блокируется, и ждет пока остальные стороны достигнут барьера
            barrier.await()
            System.out.printf("Автомобиль №%d продолжил движение.\n", carNumber)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}