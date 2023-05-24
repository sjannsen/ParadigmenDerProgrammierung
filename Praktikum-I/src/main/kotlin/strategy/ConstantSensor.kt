package strategy

class ConstantSensor(private val constantTemperature: Double): Sensor {
    override fun getTemperature(): Double {
        return constantTemperature
    }

}