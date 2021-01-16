package dev.filipebezerra.app.propertiesfrommars.propertydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.filipebezerra.app.propertiesfrommars.domain.MarsProperty

class PropertyDetailViewModel private constructor(property: MarsProperty) : ViewModel() {

    private val _property = MutableLiveData<MarsProperty>().apply {
        value = property
    }
    val property: LiveData<MarsProperty>
        get() = _property

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(property: MarsProperty) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                PropertyDetailViewModel(property) as T
        }
    }
}