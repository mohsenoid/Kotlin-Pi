plugins {
    kotlin("multiplatform") version "1.7.21"
}

group = "com.mohsenoid"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val raspberryPiTarget = linuxArm32Hfp()
    raspberryPiTarget.apply {
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

    val macTarget = macosArm64()
    macTarget.apply {
        binaries {
            executable {
                entryPoint = "com.mohsenoid.main"
                linkerOpts("-Llibs/pigpio", "-lpigpio")
            }
        }
    }

    sourceSets {
        val macosArm64Main by getting
        val macosArm64Test by getting

        val linuxArm32HfpMain by getting
        val linuxArm32HfpTest by getting

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val commonTest by getting
    }
}
