package com.mohsenoid

class Morse(private val driver: Driver) {
    fun visualize(text: String) {
        val morseCode: List<String?> = text.uppercase().map { CODES[it] }
        println("Morse Code: $morseCode")

        driver.start()

        var isFirstChart = true
        morseCode.forEach { char ->
            if (!isFirstChart && char != SPACE.toString()) {
                wordSpace()
            }
            isFirstChart = false

            var isFirstBit = true
            char?.forEach { bit ->
                if (!isFirstBit) {
                    charSpace()
                }
                isFirstBit = false

                when (bit) {
                    DOT -> driver.dot()
                    DASH -> driver.dash()
                    SPACE -> driver.space()
                    else -> error("Illegal character!")
                }
            }
        }

        driver.end()
    }

    private fun charSpace() {
        repeat(3) {
            driver.space()
        }
    }

    private fun wordSpace() {
        repeat(7) {
            driver.space()
        }
    }

    companion object {
        private const val DOT = '.'
        private const val DASH = '-'
        private const val SPACE = ' '

        private val CODES = mapOf(
            'A' to ".-", // Alpha
            'B' to "-...", // Bravo
            'C' to "-.-.", // Charlie
            'D' to "-..", // Delta
            'E' to ".", // Echo
            'F' to "..-.", // Foxtrot
            'G' to "--.", // Golf
            'H' to "....", // Hotel
            'I' to "..", // India
            'J' to ".---", // Juliet
            'K' to "-.-", // Kilo
            'L' to ".-..", // Lima
            'M' to "--", // Mike
            'N' to "-.", // November
            'O' to "---", // Oscar
            'P' to ".--.", // Papa
            'Q' to "--.-", // Quebec
            'R' to ".-.", // Romeo
            'S' to "...", // Sierra
            'T' to "-", // Tango
            'U' to "..-", // Uniform
            'V' to "...-", // Victor
            'W' to ".--", // Whiskey
            'X' to "-..-", // X
            'Y' to "-.--", // Yankee
            'Z' to "--..", // Zulu
            '0' to "-----", // Zero
            '1' to ".----", // One
            '2' to "..---", // Two
            '3' to "...--", // Three
            '4' to "....-", // Four
            '5' to ".....", // Five
            '6' to "-....", // Six
            '7' to "--...", // Seven
            '8' to "---..", // Eight
            '9' to "----.", // Nine
            '.' to ".-.-.-", // Period
            ',' to "--..--", // Comma
            ' ' to " ", // Space
        )
    }
}


