package dev.filipebezerra.app.propertiesfrommars.properties

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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

@BindingAdapter("propertiesList")
fun RecyclerView.bindPropertiesList(propertiesList: List<MarsProperty>?) = propertiesList?.let { list ->
    (adapter as MarsPropertyAdapter).submitList(list)
}