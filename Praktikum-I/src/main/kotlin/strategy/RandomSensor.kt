package strategy

import kotlin.random.Random

class RandomSensor(private val min: Double, private val max: Double) : Sensor {
    override fun getTemperature(): Double {
        return Random.nextDouble(min, max)
    }
}