package dev.filipebezerra.app.propertiesfrommars.properties

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
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

@BindingAdapter("propertiesList")
fun RecyclerView.bindPropertiesList(propertiesList: List<MarsProperty>?) = propertiesList?.let { list ->
    (adapter as MarsPropertyAdapter).submitList(list)
}

@BindingAdapter("loadStatus")
fun View.bindLoadStatus(apiStatus: MarsApiStatus?) = apiStatus?.let { status ->
    when(status) {
        MarsApiStatus.LOADING -> when(id) {
            R.id.properties_list,
            R.id.properties_load_error_layout -> isVisible = false
            R.id.properties_loading_layout -> {
                isVisible = true
                (this as ShimmerFrameLayout).startShimmer()
            }
        }
        MarsApiStatus.ERROR -> when(id) {
            R.id.properties_list -> isVisible = false
            R.id.properties_load_error_layout -> isVisible = true
            R.id.properties_loading_layout -> {
                (this as ShimmerFrameLayout).stopShimmer()
                isVisible = false
            }
        }
        MarsApiStatus.DONE -> when (id) {
            R.id.properties_list -> isVisible = true
            R.id.properties_load_error_layout -> isVisible = false
            R.id.properties_loading_layout -> {
                (this as ShimmerFrameLayout).stopShimmer()
                isVisible = false
            }
        }
    }
}