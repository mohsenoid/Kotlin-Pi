package com.mohsenoid

import kotlinx.cinterop.staticCFunction
import pi.gpio.*

const val GPIO_LED = 14
const val GPIO_BUTTON = 23

fun main() {
    println("Hello world!")
    initGPIO()
    blink()
    setupButton()
    while (true) {
    }
}

private fun initGPIO() {
    val initResult = gpioInitialise()
    if (initResult < 0) {
        println("GPIO Error initialising")
        PI_INIT_FAILED
        return
    }
}

private fun blink() {
    val ledPort = GPIO_LED.toUInt()
    initPortWithMode(port = ledPort, mode = PI_OUTPUT)

    println("Start blinking LED")
    var blinkCount = 3
    while (blinkCount > 0) {
        gpioWrite(ledPort, PI_HIGH)
        gpioSleep(PI_TIME_RELATIVE, 0, 500000)
        gpioWrite(ledPort, PI_LOW)
        gpioSleep(PI_TIME_RELATIVE, 0, 500000)
        blinkCount--
    }
}

val onButtonPressed = staticCFunction<Int, Int, UInt, Unit> { gpio, level, tick ->
    when (level) {
        0 -> {
            println("Button Pressed down, level 0")
            blink()
        }

        1 -> println("Button Released, level 1")
        2 -> println("Button GPIO timeout, no level change")
    }
}

private fun setupButton() {
    val buttonPort = GPIO_BUTTON.toUInt()
    initPortWithMode(buttonPort, PI_INPUT)

    gpioSetAlertFunc(buttonPort, onButtonPressed)
}

private fun initPortWithMode(port: UInt, mode: Int) {
    val setModeResult = gpioSetMode(port, mode.toUInt())
    if (setModeResult < 0) {
        println("Could not set mode for GPIO$port")
        return
    }
}
