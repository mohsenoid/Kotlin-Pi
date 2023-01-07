@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package com.mohsenoid

import kotlinx.cinterop.staticCFunction
import pi.gpio.*
import kotlin.properties.Delegates

const val GPIO_LED_S = 18
const val GPIO_LED_1 = 15
const val GPIO_LED_2 = 23
const val GPIO_BUTTON_1 = 14
const val GPIO_BUTTON_2 = 24

enum class GameState {
    IDLE, USER1, USER2
}

var gameState: GameState by Delegates.observable(GameState.IDLE) { property, oldValue, newValue ->
    println("Game state: $newValue")

    when (newValue) {
        GameState.IDLE -> {
            gpioWrite(GPIO_LED_1.toUInt(), PI_LOW)
            gpioWrite(GPIO_LED_2.toUInt(), PI_LOW)
            gpioWrite(GPIO_LED_S.toUInt(), PI_HIGH)
        }

        GameState.USER1 -> {
            gpioWrite(GPIO_LED_1.toUInt(), PI_HIGH)
            gpioWrite(GPIO_LED_2.toUInt(), PI_LOW)
            gpioWrite(GPIO_LED_S.toUInt(), PI_LOW)
        }

        GameState.USER2 -> {
            gpioWrite(GPIO_LED_1.toUInt(), PI_LOW)
            gpioWrite(GPIO_LED_2.toUInt(), PI_HIGH)
            gpioWrite(GPIO_LED_S.toUInt(), PI_LOW)
        }
    }
}

val buttonFunction = staticCFunction<Int, Int, UInt, Unit> { gpio, level, tick ->
    when (level) {
        1 -> {
            if (gameState != GameState.IDLE) return@staticCFunction

            gameState = when (gpio) {
                GPIO_BUTTON_1 -> GameState.USER1
                GPIO_BUTTON_2 -> GameState.USER2
                else -> error("Unknown input!")
            }
        }

        0, 2 -> {
            if (gameState == GameState.IDLE) return@staticCFunction

            when (gpio) {
                GPIO_BUTTON_1 -> if (gameState == GameState.USER1) gameState = GameState.IDLE
                GPIO_BUTTON_2 -> if (gameState == GameState.USER2) gameState = GameState.IDLE
                else -> error("Unknown input!")
            }

        }
    }
}

fun main() {
    println("Hello world!")
    initGPIO()

    setupStatus()
    setupButton(GPIO_BUTTON_1, GPIO_LED_1)
    setupButton(GPIO_BUTTON_2, GPIO_LED_2)
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

private fun setupStatus() {
    val ledPort = GPIO_LED_S.toUInt()
    initPortWithMode(port = ledPort, mode = PI_OUTPUT)

    gpioWrite(ledPort, PI_HIGH)
}

private fun setupButton(buttonIo: Int, ledIo: Int) {
    val buttonPort = buttonIo.toUInt()
    initPortWithMode(buttonPort, PI_INPUT)

    val ledPort = ledIo.toUInt()
    initPortWithMode(port = ledPort, mode = PI_OUTPUT)

    gpioSetAlertFunc(buttonPort, buttonFunction)
}

private fun initPortWithMode(port: UInt, mode: Int) {
    val setModeResult = gpioSetMode(port, mode.toUInt())
    if (setModeResult < 0) {
        println("Could not set mode for GPIO$port")
        return
    }
}
