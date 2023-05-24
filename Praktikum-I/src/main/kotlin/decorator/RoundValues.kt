package decorator

import strategy.Sensor

class RoundValues(wrappedSensor: Sensor) : BaseDecorator(wrappedSensor) {

    override fun getTemperature(): Double {
        val temperature = super.getTemperature()
        return temperature.toInt().toDouble()
    }
}