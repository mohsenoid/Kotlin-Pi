package com.mohsenoid

fun main() {
    println("Hello mac!")

    val morse = Morse(Driver())

    var input = ""
    while (!input.equals("q", true)) {
        print("Enter your text (or q to exit): ")
        input = readln()

        morse.visualize(input)
    }
}

