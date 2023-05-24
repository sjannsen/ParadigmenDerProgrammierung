package observer

interface TemperatureSubject {
    public val observers: MutableList<TemperatureObserver>
    public fun addObserver(observer: TemperatureObserver): Unit
    public fun removeObserver(observer: TemperatureObserver): Unit
}