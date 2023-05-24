package observer

interface TemperatureObserver {
    public fun update(temperature: Double): Unit
}