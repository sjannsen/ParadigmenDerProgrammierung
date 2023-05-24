package strategy

class Thermometer(private var sensor: Sensor) {

    private var lastTemperature: Double? = null

    public fun measure(times: Int): Unit {
        repeat(times) {
            println(sensor.getTemperature())
        }
    }

    public fun setSensor(sensor: Sensor): Unit {
        this.sensor = sensor
    }


}