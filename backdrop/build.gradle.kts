import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    id("com.vanniktech.maven.publish") version "0.34.0"
}

kotlin {
    androidLibrary {
        namespace = "com.kyant.backdrop"
        compileSdk = 36
        buildToolsVersion = "36.1.0"
        minSdk = 21

        withJava()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(libs.compose.ui.graphics)
        }
        val skikoMain by creating {
            dependsOn(commonMain.get())
        }
        iosMain.get().dependsOn(skikoMain)
        jvmMain.get().dependsOn(skikoMain)
    }

    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
            "-Xexpect-actual-classes"
        )
    }
}

//Maven local
//mavenPublishing {
//    coordinates(
//        groupId = "com.opty.backdrop",
//        artifactId = "backdrop",
//        version = "1.0.0-LOCAL"
//    )
//
//    pom {
//        name.set("Backdrop")
//        description.set("Multiplatform Backdrop component for Compose")
//        url.set("https://github.com/xephosbot/AndroidLiquidGlass")
//
//        licenses {
//            license {
//                name.set("The Apache License, Version 2.0")
//                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//            }
//        }
//
//        developers {
//            developer {
//                id.set("xephosbot")
//                name.set("com.opty.backdrop")
//            }
//        }
//
//        scm {
//            url.set("https://github.com/xephosbot/AndroidLiquidGlass")
//            connection.set("scm:git:git://github.com/xephosbot/AndroidLiquidGlass.git")
//            developerConnection.set("scm:git:ssh://git@github.com/xephosbot/AndroidLiquidGlass.git")
//        }
//    }
//}

mavenPublishing {
    coordinates(
        groupId = "com.opty.backdrop",
        artifactId = "backdrop",
        version = "1.0.0-1"
    )

    pom {
        name.set("Backdrop")
        description.set("Multiplatform Backdrop component for Compose")
        url.set("https://github.com/opty-macros/AndroidLiquidGlass")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("opty-macros")
                name.set("com.opty.backdrop")
            }
        }

        scm {
            url.set("https://github.com/opty-macros/AndroidLiquidGlass")
            connection.set("scm:git:git://github.com/opty-macros/AndroidLiquidGlass.git")
            developerConnection.set("scm:git:ssh://git@github.com/opty-macros/AndroidLiquidGlass.git")
        }
    }
//    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.DEFAULT, automaticRelease = false)

}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/opty-macros/AndroidLiquidGlass")
                credentials {
                    username = System.getenv("GH_PACKAGES_USERNAME")
                    password = System.getenv("GH_PACKAGES_TOKEN")
                }
            }
        }
    }
}
