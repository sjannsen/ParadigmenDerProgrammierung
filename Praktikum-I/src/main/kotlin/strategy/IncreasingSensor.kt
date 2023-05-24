package strategy

class IncreasingSensor(private var startTemperature: Double): Sensor {
    override fun getTemperature(): Double {
        startTemperature += 0.5
        return startTemperature // - 0.5
    }
}