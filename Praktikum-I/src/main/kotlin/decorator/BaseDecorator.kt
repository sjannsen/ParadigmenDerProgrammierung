package decorator

import strategy.Sensor

abstract class BaseDecorator(private val wrappedSensor: Sensor): Sensor {

    override fun getTemperature(): Double {
        return wrappedSensor.getTemperature()
    }

}