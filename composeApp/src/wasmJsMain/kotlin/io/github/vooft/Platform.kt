package io.github.vooft

class WasmPlatform {
    val name: String = "Web with Kotlin/Wasm"
}

fun getPlatform() = WasmPlatform()