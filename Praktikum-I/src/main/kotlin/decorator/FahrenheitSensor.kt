package decorator

import strategy.Sensor

class FahrenheitSensor(wrappedSensor: Sensor) : BaseDecorator(wrappedSensor) {

    /*
    °C = (°F - 32) * 5/9 (von Fahrenheit in Celsius)
    °F = °C * 1,8 + 32 (von Celsius nach Fahrenheit)
    */

    override fun getTemperature(): Double {
        return super.getTemperature() * 1.8 + 32
    }


}