package by.next.way.threads.example.phaser

import java.util.*
import java.util.concurrent.Phaser

fun main() {
    val phaser = Phaser(1)//Сразу регистрируем главный поток
    //Фазы 0 и 6 - это автобусный парк, 1 - 5 остановки
    val passengers = ArrayList<Passenger>()

    for (i in 1..4) {           //Сгенерируем пассажиров на остановках
        if ((Math.random() * 2).toInt() > 0)
            passengers.add(Passenger(
                    phaser = phaser,
                    departure = i,
                    destination = i + 1
            ))//Этот пассажир выходит на следующей

        if ((Math.random() * 2).toInt() > 0)
            passengers.add(Passenger(
                    phaser = phaser,
                    departure = i,
                    destination = 5
            ))    //Этот пассажир выходит на конечной
    }

    for (i in 0..6) {
        when (i) {
            0 -> {
                println("Автобус выехал из парка.")
                phaser.arrive()//В фазе 0 всего 1 участник - автобус
            }
            6 -> {
                println("Автобус уехал в парк.")
                phaser.arriveAndDeregister()//Снимаем главный поток, ломаем барьер
            }
            else -> {
                val currentBusStop = phaser.phase
                println("Остановка № $currentBusStop")

                for (p in passengers)
                //Проверяем, есть ли пассажиры на остановке
                    if (p.departure == currentBusStop) {
                        phaser.register()//Регистрируем поток, который будет участвовать в фазах
                        p.start()        // и запускаем
                    }

                phaser.arriveAndAwaitAdvance()//Сообщаем о своей готовности
            }
        }
    }
}


class Passenger(
        private val phaser: Phaser,
        val departure: Int,
        private val destination: Int
) : Thread() {

    init {
        println(this.toString() + " ждёт на остановке № " + this.departure)
    }

    override fun run() {
        try {
            println("$this сел в автобус.")

            while (phaser.phase < destination) {
                //Пока автобус не приедет на нужную остановку(фазу)
                phaser.arriveAndAwaitAdvance()     //заявляем в каждой фазе о готовности и ждем
            }

            sleep(1)
            println("$this покинул автобус.")
            phaser.arriveAndDeregister()   //Отменяем регистрацию на нужной фазе
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    override fun toString(): String {
        return "Пассажир{$departure -> $destination}"
    }
}