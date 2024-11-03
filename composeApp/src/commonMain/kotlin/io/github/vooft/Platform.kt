package io.github.vooft

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform