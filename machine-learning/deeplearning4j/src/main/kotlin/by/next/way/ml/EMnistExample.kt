package by.next.way.ml

import com.google.common.collect.Lists
import org.apache.logging.log4j.LogManager
import org.deeplearning4j.datasets.iterator.impl.EmnistDataSetIterator
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration.Builder
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver
import org.deeplearning4j.earlystopping.scorecalc.DataSetLossCalculator
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition
import org.deeplearning4j.earlystopping.termination.MaxTimeIterationTerminationCondition
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.DenseLayer
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.deeplearning4j.util.ModelSerializer
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.learning.config.Adam
import org.nd4j.linalg.lossfunctions.LossFunctions
import java.io.File
import java.util.concurrent.TimeUnit

object EMnistExample {
    private val log = LogManager.getLogger()
    private const val BATCH_SIZE = 16 // how many examples to simultaneously train in the network
    private val EMNIST_SET: EmnistDataSetIterator.Set = EmnistDataSetIterator.Set.BALANCED
    private const val RNG_SEED = 123L
    private const val NUM_ROWS = 28
    private const val NUM_COLUMNS = 28
    private const val REPORTING_INTERVAL = 5
    private const val MODEL_FILE_NAME = "kek.dl4j"

    @Throws(java.io.IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // create the data iterators for emnist
        val emnistTrain = EmnistDataSetIterator(EMNIST_SET, BATCH_SIZE, true)
        val emnistTest = EmnistDataSetIterator(EMNIST_SET, BATCH_SIZE, false)

        val outputNum = EmnistDataSetIterator.numLabels(EMNIST_SET)

        // network configuration (not yet initialized)
        val multiLayerConfiguration = NeuralNetConfiguration.Builder()
                .seed(RNG_SEED)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Adam())
                .l2(1e-4)
                .list()
                .layer(DenseLayer.Builder()
                        .nIn(NUM_ROWS * NUM_COLUMNS) // Number of input datapoints.
                        .nOut(1000) // Number of output datapoints.
                        .activation(Activation.RELU) // Activation function.
                        .weightInit(WeightInit.XAVIER) // Weight initialization.
                        .build())
                .layer(OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(1000)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .pretrain(false).backprop(true)
                .build()


        // create the MLN
        var multiLayerNetwork: MultiLayerNetwork
        try {
            multiLayerNetwork = ModelSerializer.restoreMultiLayerNetwork(MODEL_FILE_NAME)
        } catch (e: Exception) {
            log.warn("error loaded")
            multiLayerNetwork = MultiLayerNetwork(multiLayerConfiguration)
            multiLayerNetwork.init()
        }
        val items = Lists.newArrayList(emnistTest.iterator())
        for (i in 0 until items.size) {
            val item = items[i]
            item.labelNames = emnistTest.labels
            val result = multiLayerNetwork.predict(item)
            log.info("\n" + arrayToString(item.features))
            val trueValue = emnistTest.labels[item.labels.argMax().amaxNumber().toInt()]
            log.info(result[0] + " and " + trueValue)
            if (result[0] != trueValue) {
                break
            }
        }

        // pass a training listener that reports score every N iterations
        multiLayerNetwork.addListeners(ScoreIterationListener(REPORTING_INTERVAL))

        // here we set up an early stopping trainer
        // early stopping is useful when your trainer runs for
        // a long time or you need to programmatically stop training
        val earlyStoppingConfiguration = Builder<MultiLayerNetwork>()
                .epochTerminationConditions(MaxEpochsTerminationCondition(1))
                .iterationTerminationConditions(MaxTimeIterationTerminationCondition(20, TimeUnit.MINUTES))
                .scoreCalculator(DataSetLossCalculator(emnistTest, true))
                .evaluateEveryNEpochs(1)
                .modelSaver(LocalFileModelSaver(System.getProperty("user.dir")))
                .build()

        // training
        val earlyStoppingTrainer = EarlyStoppingTrainer(earlyStoppingConfiguration, multiLayerNetwork, emnistTrain)
        val earlyStoppingResult = earlyStoppingTrainer.fit()

        // print out early stopping results
        log.info("Termination reason: " + earlyStoppingResult.terminationReason)
        log.info("Termination details: " + earlyStoppingResult.terminationDetails)
        log.info("Total epochs: " + earlyStoppingResult.totalEpochs)
        log.info("Best epoch number: " + earlyStoppingResult.bestModelEpoch)
        log.info("Score at best epoch: " + earlyStoppingResult.bestModelScore)
        // evaluate basic performance
        val evaluation = multiLayerNetwork.evaluate(emnistTest)
        log.info(evaluation.accuracy())
        log.info(evaluation.precision())
        log.info(evaluation.recall())

        // evaluate ROC and calculate the Area Under Curve
        val rocMultiClass = multiLayerNetwork.evaluateROCMultiClass(emnistTest)
        log.info(rocMultiClass.calculateAverageAUC())

        // calculate AUC for a single class
        val classIndex = 0
        log.info(rocMultiClass.calculateAUC(classIndex))

        // optionally, you can print all stats from the evaluations
        log.info(evaluation.stats())
        log.info(rocMultiClass.stats())
        ModelSerializer.writeModel(multiLayerNetwork, File(MODEL_FILE_NAME), true)
    }

    fun arrayToString(array: INDArray): String {
        val result = StringBuilder()
        for (i in 0..NUM_ROWS) {
            for (j in 0..NUM_COLUMNS.toLong()) {
                val atom = array.data().getFloat(i * NUM_COLUMNS + j)
                if (atom == 0f) {
                    result.append("0")
                } else {
                    result.append("1")
                }
            }
            result.append("\n")
        }
        return result.toString()
    }
}