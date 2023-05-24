package observer

class HeatingSystemObserver(private val lowerLimit: Double, private val upperLimit: Double): TemperatureObserver {

    private val lastFiveTemperaturesList: MutableList<Double> = mutableListOf()

    override fun update(temperature: Double) {
        lastFiveTemperaturesList.add(temperature)

        if (lastFiveTemperaturesList.size < 5)
            return

        val averageTemperature: Double = getAvgTemperature()
        println("Durchschnittstemperatur der letzten 5 Messungen: ${averageTemperature}")

        if (averageTemperature < lowerLimit)
            println("Heizung an")

        if (averageTemperature > upperLimit)
            println("Heizung aus")

        lastFiveTemperaturesList.clear()
    }

    private fun getAvgTemperature(): Double {
        return lastFiveTemperaturesList.average()
    }
}