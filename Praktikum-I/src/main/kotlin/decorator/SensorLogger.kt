package decorator

import strategy.Sensor

class SensorLogger(wrappedSensor: Sensor) : BaseDecorator(wrappedSensor) {

    override fun getTemperature(): Double {
        val temperature = super.getTemperature()

        println(temperature)
        return temperature
    }
}