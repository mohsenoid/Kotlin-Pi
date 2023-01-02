package com.mohsenoid

import pi.gpio.*
import platform.posix.sleep

actual class Driver {

    private val ledPort = GPIO_LED.toUInt()

    init {
        initGPIO()
        initPortWithMode(port = ledPort, mode = PI_OUTPUT)
    }

    actual fun start() {
        turnOff()
    }

    actual fun dot() {
        turnOn()
        sleep(DELAY_DOT.toUInt())
        turnOff()
    }

    actual fun dash() {
        turnOn()
        sleep(DELAY_DASH.toUInt())
        turnOff()
    }

    actual fun space() {
        sleep(DELAY_DASH.toUInt())
    }

    actual fun end() {
        turnOff()
    }

    private fun initGPIO() {
        val initResult = gpioInitialise()
        if (initResult < 0) {
            println("GPIO Error initialising")
            PI_INIT_FAILED
            return
        }
    }

    private fun turnOn() {
        gpioWrite(ledPort, PI_HIGH)
    }

    private fun turnOff() {
        gpioWrite(ledPort, PI_LOW)
    }

    private fun initPortWithMode(port: UInt, mode: Int) {
        val setModeResult = gpioSetMode(port, mode.toUInt())
        if (setModeResult < 0) {
            println("Could not set mode for GPIO$port")
            return
        }
    }

    companion object {
        private const val GPIO_LED = 23

        private const val DELAY = 0.5
        private const val DELAY_DOT = DELAY
        private const val DELAY_DASH = 3 * DELAY
    }
}