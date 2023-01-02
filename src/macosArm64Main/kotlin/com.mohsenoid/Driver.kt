package com.mohsenoid

actual class Driver {

    actual fun start() {
        println()
    }

    actual fun dot() {
        print(".")
    }

    actual fun dash() {
        print("-")
    }

    actual fun space() {
        print("_")
    }

    actual fun end() {
        println()
    }
}