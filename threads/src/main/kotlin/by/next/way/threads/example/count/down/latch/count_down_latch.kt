package by.next.way.threads.example.count.down.latch

import java.util.concurrent.CountDownLatch

fun main() {
    val race = Race()
    for (i in 1..5) {
        Thread(Car(
                race = race,
                carNumber = i,
                carSpeed = (Math.random() * 100 + 50).toInt())
        ).start()
        Thread.sleep(1000)
    }

    while (race.start.count > 3)
    //Проверяем, собрались ли все автомобили
        Thread.sleep(100)              //у стартовой прямой. Если нет, ждем 100ms

    Thread.sleep(1000)
    println("На старт!")
    race.start.countDown()//Команда дана, уменьшаем счетчик на 1
    Thread.sleep(1000)
    println("Внимание!")
    race.start.countDown()//Команда дана, уменьшаем счетчик на 1
    Thread.sleep(1000)
    println("Марш!")
    race.start.countDown()//Команда дана, уменьшаем счетчик на 1
    //счетчик становится равным нулю, и все ожидающие потоки
    //одновременно разблокируются
}

data class Race(
        val start: CountDownLatch = CountDownLatch(8),
        val trackLength: Int = 500000
)

class Car(
        val race: Race,
        val carNumber: Int,
        val carSpeed: Int//считаем, что скорость автомобиля постоянная
) : Runnable {

    override fun run() {
        try {
            System.out.printf("Автомобиль №%d подъехал к стартовой прямой.\n", carNumber)
            //Автомобиль подъехал к стартовой прямой - условие выполнено
            //уменьшаем счетчик на 1
            race.start.countDown()
            //метод await() блокирует поток, вызвавший его, до тех пор, пока
            //счетчик CountDownLatch не станет равен 0
            race.start.await()
            Thread.sleep((race.trackLength / carSpeed).toLong())//ждем пока проедет трассу
            System.out.printf("Автомобиль №%d финишировал!\n", carNumber)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}