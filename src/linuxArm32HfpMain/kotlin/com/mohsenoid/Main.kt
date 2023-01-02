package com.mohsenoid

import pi.gpio.*

const val GPIO_LED = 14

fun main() {
    println("Hello world!")
    initGPIO()
    blink()
}

private fun initGPIO() {
    if (gpioInitialise() < 0) {
        println("GPIO Error initialising")
        return
    }
}

private fun blink() {
    val ledPort = GPIO_LED.toUInt()
    initPortWithMode(port = ledPort, mode = PI_OUTPUT)

    println("Start blinking LED")
    var blinkCount = 3
    while (blinkCount > 0) {
        gpioWrite(ledPort, PI_LOW)
        gpioSleep(PI_TIME_RELATIVE, 0, 500000)
        gpioWrite(ledPort, PI_HIGH)
        gpioSleep(PI_TIME_RELATIVE, 0, 500000)
        blinkCount--
    }
}

private fun initPortWithMode(port: UInt, mode: Int) {
    if (gpioSetMode(port, mode.toUInt()) < 0) {
        println("Could not set mode for GPIO$port")
        return
    }
}
