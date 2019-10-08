package by.next.way.neural.network.app

import by.next.way.neural.network.easy.ComplexML
import by.next.way.neural.network.easy.NeuralNetwork
import org.apache.logging.log4j.LogManager
import java.io.File
import javax.imageio.ImageIO

object Test {

    private val log = LogManager.getLogger()
    private const val MNIST_FOLDER = "/home/cstupakevich/tmp/mnist"
    private const val MNIST_TEST_FOLDER = "$MNIST_FOLDER/test/"
    private const val MNIST_TRAIN_FOLDER = "$MNIST_FOLDER/train/"

    @JvmStatic
    fun main(args: Array<String>) {
        trainDemo()
    }

    fun checkResults() {
        val mapMLs: HashMap<Int, NeuralNetwork> = hashMapOf()
        for (letter in 0..9) {
            mapMLs[letter] = NeuralNetworkWebImageApp.loadML("$letter.ml")
            log.info("ML loaded [$letter.ml]")
        }
        val folder = File(MNIST_TRAIN_FOLDER)
        val files = folder.listFiles()
        for (file in files) {
            val item = convertForML_lazy(file)
            for (ml in mapMLs) {
                log.info("${ml.key} -> ${ml.value.predictionStr(item.second[0])}")
            }
        }
    }

    fun trainDemo() {
        val folder = File(MNIST_TRAIN_FOLDER)
        val files = folder.listFiles()
        log.info("Start prepare data")
        val data: MutableList<MutableList<MutableList<Double>>> = mutableListOf()
        for (file in files) {
            val item = convertForML_1_plus_1(file)
            item.second.add(when (item.first) {
                0 -> mutableListOf(0.1)
                1 -> mutableListOf(0.2)
                2 -> mutableListOf(0.3)
                3 -> mutableListOf(0.4)
                4 -> mutableListOf(0.5)
                5 -> mutableListOf(0.6)
                6 -> mutableListOf(0.7)
                7 -> mutableListOf(0.8)
                8 -> mutableListOf(0.9)
                9 -> mutableListOf(1.0)
                else -> TODO("UNKNOWN LETTER")
            })
            data.add(item.second)
        }
        log.info("Dataset size: ${data[0][0].size} and ${data[0][1].size}")
        log.info("Create MLs [0]")
        val complexML = ComplexML()
        complexML.addNeuralNetwork(NeuralNetwork(
                key = "demo.ml",
                numInputs = data[0][0].size,
                numOutputs = data[0][1].size
        ))
        log.info("Start training [0]")
        for (i in 0..100) {
            complexML.train(data, 1000)
        }
        log.info("Start check results [0]")
        val finalFolder = File(MNIST_TRAIN_FOLDER)
        val finalFiles = finalFolder.listFiles()
        for (file in finalFiles) {
            val item = convertForML_1_plus_1(file)
            val prediction = complexML.prediction(item.second[0])
            log.info("${file.name} -> $prediction")
            loadImage(file)
        }
        log.info("Finish all")
    }

    fun trainMLS() {
        val folder = File(MNIST_TEST_FOLDER)
        val files = folder.listFiles()
        val dataMLs: HashMap<Int, MutableList<MutableList<MutableList<Double>>>> = hashMapOf()
        for (letter in 0..9) {
            dataMLs[letter] = mutableListOf()
        }
        log.info("Start prepare data")
        for (file in files) {
            val item = convertForML_lazy(file)
            for (data in dataMLs) {
                if (item.first == data.key) {
                    item.second.add(mutableListOf(1.0))
                } else {
                    item.second.add(mutableListOf(0.0))
                }
                data.value.add(item.second)
            }
        }
        for (letter in 0..9) {
            log.info("Create MLs [$letter]")
            val data = dataMLs[letter]!!
            val neuralNetwork = NeuralNetwork(data[0][0].size, data[0][1].size)
            neuralNetwork.train(data)
            log.info("Start training [$letter]")
            NeuralNetworkWebImageApp.saveML(neuralNetwork, "$letter.ml")
        }
        log.info("Finish all")
    }

    fun convertForML_lazy(file: File): Pair<Int, MutableList<MutableList<Double>>> {
        val fileName = file.name
        val fileNameParts = fileName.split(".")
        val letter = fileNameParts[1].toInt()
        val image = ImageIO.read(file)
        val width = image.width
        val height = image.height
        val input = mutableListOf<Double>()
        for (x in 0 until height) {
            for (y in (0 until width)) {
                val colour = image.getRGB(y, x)
                if (colour == -1) {
                    input.add(0.0)
                } else {
                    input.add(1.0)
                }
            }
        }
        return Pair(letter, mutableListOf(input))
    }

    fun convertForML_1(file: File): Pair<Int, MutableList<MutableList<Double>>> {
        val fileName = file.name
        val fileNameParts = fileName.split(".")
        val letter = fileNameParts[1].toInt()
        val image = ImageIO.read(file)
        val width = image.width
        val height = image.height
        val input = mutableListOf<Double>()
        for (x in 0 until height) {
            var result = 0.0
            for (y in (0 until width)) {
                val colour = image.getRGB(y, x)
                val multi = 1.0 / width
                result += if (colour == -1) {
                    0.0
                } else {
                    multi
                }
            }
            input.add(result)
        }
        return Pair(letter, mutableListOf(input))
    }


    fun convertForML_1_plus_1(file: File): Pair<Int, MutableList<MutableList<Double>>> {
        val fileName = file.name
        val fileNameParts = fileName.split(".")
        val letter = fileNameParts[1].toInt()
        val image = ImageIO.read(file)
        val width = image.width
        val height = image.height
        val input = mutableListOf<Double>()
        for (x in 0 until height) {
            var result = 0.0
            for (y in (0 until width)) {
                val colour = image.getRGB(y, x)
                val multi = 1.0 / width
                result += if (colour == -1) {
                    0.0
                } else {
                    multi
                }
            }
            input.add(result)
        }
        for (x in 0 until width) {
            var result = 0.0
            for (y in (0 until height)) {
                val colour = image.getRGB(y, x)
                val multi = 1.0 / width
                result += if (colour == -1) {
                    0.0
                } else {
                    multi
                }
            }
            input.add(result)
        }
        return Pair(letter, mutableListOf(input))
    }

    fun loadImage(file: File) {
        val image = ImageIO.read(file)
        val width = image.width
        val height = image.height
        log.info("width=$width and height=$height")
        for (x in 0 until height) {
            for (y in (0 until width)) {
                val colour = image.getRGB(y, x)
                if (colour == -1) {
                    print("_")
                } else {
                    print("*")
                }
            }
            println("")
        }
    }


}