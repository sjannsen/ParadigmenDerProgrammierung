package observer

class TemperatureAlert(private val temperatureAlertLimit: Double, private val alertMessage: String):
    TemperatureObserver {

    override fun update(temperature: Double): Unit {
        if (temperature >= temperatureAlertLimit)
            println(alertMessage)
    }
}