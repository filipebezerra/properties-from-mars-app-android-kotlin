package dev.filipebezerra.app.propertiesfrommars.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class PropertyType(val type: String) {
    RENT("rent"),
    BUY("buy")
}

@Parcelize data class MarsProperty(
    val id: String,
    val type: PropertyType,
    val price: Double,
    val imageUrl: String,
) : Parcelable