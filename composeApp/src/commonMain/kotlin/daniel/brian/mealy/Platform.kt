package daniel.brian.mealy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform