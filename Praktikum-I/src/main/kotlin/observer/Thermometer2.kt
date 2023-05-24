package observer

import strategy.Sensor

class Thermometer2(private var sensor: Sensor): TemperatureSubject {

    override val observers: MutableList<TemperatureObserver> = mutableListOf()
    private var lastTemperature: Double? = null

    public fun measure(times: Int): Unit {
        repeat(times) {
            val temperature = sensor.getTemperature()


            if (temperature != lastTemperature)
                notifyObservers(temperature)

            lastTemperature = temperature

        }
    }

    public fun setSensor(sensor: Sensor): Unit {
        this.sensor = sensor
    }

    override fun addObserver(observer: TemperatureObserver) {
        observers.add(observer)
    }

    override fun removeObserver(observer: TemperatureObserver) {
        observers.remove(observer)
    }


    private fun notifyObservers(temperature: Double): Unit {
        for (observer: TemperatureObserver in observers)
            observer.update(temperature)
    }
}