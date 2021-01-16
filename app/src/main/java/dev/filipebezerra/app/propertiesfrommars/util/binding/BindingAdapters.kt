package dev.filipebezerra.app.propertiesfrommars.util.binding

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import dev.filipebezerra.app.propertiesfrommars.R
import dev.filipebezerra.app.propertiesfrommars.domain.MarsProperty

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(imageUrl: String?) = imageUrl?.let { url ->
    val imageUri = url.toUri().buildUpon().scheme("https").build()
    Glide.with(context)
        .load(imageUri)
        .placeholder(R.drawable.loading_img)
        .error(R.drawable.ic_broken_image)
        .into(this)
}

@BindingAdapter("imageContentDescription")
fun ImageView.bindImageContentDescription(marsProperty: MarsProperty?) = marsProperty?.let { property ->
    contentDescription = context.getString(
        R.string.property_image_content_description,
        property.type.type,
        property.price
    )
}