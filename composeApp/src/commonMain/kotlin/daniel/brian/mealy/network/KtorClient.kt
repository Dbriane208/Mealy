package daniel.brian.mealy.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {
    val httpClient = HttpClient {
        // content negotiation to pass json directly
        install(ContentNegotiation){
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    useAlternativeNames = false
                    coerceInputValues = true
                }
            )
        }
    }
}
