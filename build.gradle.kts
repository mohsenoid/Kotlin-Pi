plugins {
    kotlin("multiplatform") version "1.7.21"
}

group = "com.mohsenoid"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val nativeTarget = linuxArm32Hfp()

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.mohsenoid.main"
                linkerOpts("-Llibs/pigpio", "-lpigpio")
            }

            compilations.getByName("main") {
                val pigpio by cinterops.creating {
                    includeDirs("includes/pigpio/")
                }
            }
        }
    }

    sourceSets {
        val linuxArm32HfpMain by getting
        val linuxArm32HfpTest by getting
    }
}
