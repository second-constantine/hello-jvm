package by.next.way.threads.example

import java.util.concurrent.Semaphore


fun main() {
    for (i in 1..7) {
        Thread(Car(i)).start()
        Thread.sleep(400)
    }
}

data class Parking(
        //Парковочное место занято - true, свободно - false
        val parkingPlaces: BooleanArray = BooleanArray(5),
        //Устанавливаем флаг "справедливый", в таком случае метод
        //aсquire() будет раздавать разрешения в порядке очереди
        val semaphore: Semaphore = Semaphore(5, true)
)

class Car(
        private val carNumber: Int
) : Runnable {

    override fun run() {
        System.out.printf("Автомобиль №%d подъехал к парковке.\n", carNumber)
        try {
            val parking = Parking()
            //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
            //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
            //пока семафор не разрешит доступ
            parking.semaphore.acquire()

            var parkingNumber = -1

            //Ищем свободное место и паркуемся
            synchronized(parking.parkingPlaces) {
                for (i in 0..4)
                    if (!parking.parkingPlaces[i]) {      //Если место свободно
                        parking.parkingPlaces[i] = true  //занимаем его
                        parkingNumber = i         //Наличие свободного места, гарантирует семафор
                        System.out.printf("Автомобиль №%d припарковался на месте %d.\n", carNumber, i)
                        break
                    }
            }

            Thread.sleep(5000)       //Уходим за покупками, к примеру

            synchronized(parking.parkingPlaces) {
                parking.parkingPlaces[parkingNumber] = false//Освобождаем место
            }

            //release(), напротив, освобождает ресурс
            parking.semaphore.release()
            System.out.printf("Автомобиль №%d покинул парковку.\n", carNumber)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}