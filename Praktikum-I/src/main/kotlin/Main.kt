import observer.HeatingSystemObserver
import observer.TemperatureAlert
import observer.Thermometer2
import strategy.ConstantSensor
import decorator.FahrenheitSensor
import decorator.RoundValues
import decorator.SensorLogger
import strategy.IncreasingSensor
import strategy.RandomSensor
import strategy.Thermometer

fun main() {

    // Aufgabe 1 Strategien für Temperaturwerte
    println("Aufgabe 1")

    val randomSensor = RandomSensor(min = 2.0, max = 8.0) // liefert zufällige Temperaturen zwischen 2.0 und 8.0 Grad
    println(" Random Strategy.Sensor ")
    repeat(4) {
        println(randomSensor.getTemperature())
    }
    val constantSensor = ConstantSensor(constantTemperature = 21.5) // liefert jedes Mal 21.5 Grad
    println(" Constant Strategy.Sensor ")
    repeat(4) {
        println(constantSensor.getTemperature())
    }

    val increasingSensor =
        IncreasingSensor(startTemperature = 15.0) // fängt bei 15 Grad an und erhöht jedes mal die Temperatur um 0.5 Grad
    println(" Increasing Strategy.Sensor ")
    repeat(4) {
        println(increasingSensor.getTemperature())
    }


    // Aufgabe 2 Strategien verwenden

    // b)
    // Strategy.Thermometer mit erster Strategie initialisieren
    println("Aufgabe 2")
    println("b.)")
    val thermometer = Thermometer(sensor = RandomSensor(2.0, 8.0))
    thermometer.measure(10)


    // c)
    println("c.)")
    thermometer.setSensor(IncreasingSensor(startTemperature = 15.0))  // Strategie wechseln
    thermometer.measure(10)
    println()


    // Aufgabe 3 Sensoren dekorieren
    println("Aufgabe 3")

    // b)
    println("b.)")

    val sensor1: SensorLogger = SensorLogger(RoundValues(RandomSensor(2.0, 5.0)))
    sensor1.getTemperature()
    println()

    val sensor2: SensorLogger = SensorLogger(RoundValues(FahrenheitSensor(IncreasingSensor(19.5))))
    sensor2.getTemperature()
    sensor2.getTemperature()
    sensor2.getTemperature()
    println()

    val sensor3: SensorLogger = SensorLogger(RoundValues(FahrenheitSensor(SensorLogger(IncreasingSensor(19.5)))))
    sensor3.getTemperature()
    sensor3.getTemperature()
    sensor3.getTemperature()
    println()


    // c)
    println("c.)")

    val t1 = Thermometer(SensorLogger(RoundValues(RandomSensor(2.0, 5.0))))
    t1.measure(2)
    println()

    val t2 = Thermometer(RoundValues(SensorLogger(RandomSensor(2.0, 5.0))))
    t2.measure(2)
    println()

    // Aufgabe 4 Beobachten des Thermometers
    println("Aufgabe 4")

    //c)
    println("c.)")

    val sensor = SensorLogger(RoundValues(RandomSensor(10.0, 50.0)))
    val thermometer1 = Thermometer2(sensor)
    val alertObserver = TemperatureAlert(
        temperatureAlertLimit = 30.0,
        alertMessage = "Ganz schön heiß"
    )
    val heatingSystemObserver = HeatingSystemObserver(
        upperLimit = 23.0,
         lowerLimit = 19.0
    )
    thermometer1.addObserver(alertObserver)
    thermometer1.addObserver(heatingSystemObserver)
    thermometer1.measure(20)
}