package dev.filipebezerra.app.propertiesfrommars.properties

import androidx.lifecycle.*
import dev.filipebezerra.app.propertiesfrommars.R
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsApiService
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsPropertyNetwork
import dev.filipebezerra.app.propertiesfrommars.util.event.Event
import dev.filipebezerra.app.propertiesfrommars.util.ext.postEvent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class PropertiesViewModel(private val marsApiService: MarsApiService) : ViewModel() {

    private val _properties = MutableLiveData<String>()
    val properties: LiveData<String>
        get() = _properties

    private val _snackbarTextResource = MutableLiveData<Event<Int>>()
    val snackbarTextResource: LiveData<Event<Int>>
        get() = _snackbarTextResource

    init {
        loadProperties()
    }

    private fun loadProperties() {
        viewModelScope.launch {
            try {
                handleGetPropertiesResponse(marsApiService.getProperties())
            } catch (error: HttpException) {
                handleGetPropertiesHttpFailure(error)
            } catch (error: Exception) {
                handleGetPropertiesFailure(error)
            }
        }
    }

    private fun handleGetPropertiesResponse(properties: List<MarsPropertyNetwork>) {
        _properties.value = "We got ${properties.size} properties available right now!"
    }

    private fun handleGetPropertiesHttpFailure(error: HttpException) {
        Timber.e(error, "Something goes wrong trying to load properties")
        _snackbarTextResource.postEvent(R.string.unsuccessful_get_properties_response)
    }

    private fun handleGetPropertiesFailure(error: Exception) {
        Timber.e(error, "Something goes wrong trying to parse properties")
        _snackbarTextResource.postEvent(R.string.unsuccessful_get_properties_response)
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun factory(marsApiService: MarsApiService) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                PropertiesViewModel(marsApiService) as T
        }
    }
}