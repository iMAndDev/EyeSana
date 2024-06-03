package ua.nure.maksymburym.eyesana.app

import kotlinx.serialization.json.Json

object CommonModule {
    val json: Json by lazy {
        Json {
            encodeDefaults = true
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}