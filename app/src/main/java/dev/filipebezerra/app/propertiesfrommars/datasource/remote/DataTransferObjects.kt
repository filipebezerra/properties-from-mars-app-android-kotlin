package dev.filipebezerra.app.propertiesfrommars.datasource.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.filipebezerra.app.propertiesfrommars.domain.MarsProperty
import dev.filipebezerra.app.propertiesfrommars.domain.PropertyType

@JsonClass(generateAdapter = true)
data class MarsPropertyNetwork(
    @Json(name = "id") val id: String,
    @Json(name = "type") val type: String,
    @Json(name = "price") val price: Long,
    @Json(name = "img_src") val imageSrc: String,
)

fun List<MarsPropertyNetwork>.toDomainModels() = map {
    MarsProperty(
        id = it.id,
        type = PropertyType.valueOf(it.type),
        price = it.price.toDouble(),
        imageUrl = it.imageSrc,
    )
}