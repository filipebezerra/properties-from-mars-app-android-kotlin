package dev.filipebezerra.app.propertiesfrommars.propertydetail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.filipebezerra.app.propertiesfrommars.R
import dev.filipebezerra.app.propertiesfrommars.domain.PropertyType

@BindingAdapter("propertyType")
fun TextView.bindPropertyType(propertyType: PropertyType?) = propertyType?.let { type ->
    text = context.getString(R.string.property_type_format, type.type)
}

@BindingAdapter("propertyPrice")
fun TextView.bindPropertyPrice(propertyPrice: Double?) = propertyPrice?.let { price ->
    text = context.getString(R.string.property_price_format, price)
}