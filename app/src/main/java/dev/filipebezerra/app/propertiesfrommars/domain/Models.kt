package dev.filipebezerra.app.propertiesfrommars.domain

enum class PropertyType(val type: String) {
    RENT("rent"),
    BUY("buy")
}

data class MarsProperty(
    val id: String,
    val type: PropertyType,
    val price: Double,
    val imageUrl: String,
)