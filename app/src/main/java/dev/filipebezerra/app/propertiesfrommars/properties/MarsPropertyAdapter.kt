package dev.filipebezerra.app.propertiesfrommars.properties

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.filipebezerra.app.propertiesfrommars.databinding.PropertyItemBinding
import dev.filipebezerra.app.propertiesfrommars.databinding.PropertyItemBinding.inflate
import dev.filipebezerra.app.propertiesfrommars.domain.MarsProperty

class MarsPropertyAdapter :
    ListAdapter<MarsProperty, MarsPropertyViewHolder>(MarsPropertyItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MarsPropertyViewHolder.createFrom(parent)

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) =
        holder.bindTo(getItem(position))
}

object MarsPropertyItemCallback : DiffUtil.ItemCallback<MarsProperty>() {
    override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty) =
        oldItem.id === newItem.id

    override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty) =
        oldItem == newItem
}

class MarsPropertyViewHolder private constructor(
    private val itemViewBinding: PropertyItemBinding
) : RecyclerView.ViewHolder(itemViewBinding.root) {
    fun bindTo(item: MarsProperty) = with(itemViewBinding) {
        property = item
        executePendingBindings()
    }

    companion object {
        fun createFrom(parent: ViewGroup) = MarsPropertyViewHolder(
            inflate(from(parent.context), parent, false)
        )
    }
}