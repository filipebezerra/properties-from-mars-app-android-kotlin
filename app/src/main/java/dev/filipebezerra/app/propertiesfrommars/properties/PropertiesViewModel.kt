package dev.filipebezerra.app.propertiesfrommars.properties

import androidx.lifecycle.*
import dev.filipebezerra.app.propertiesfrommars.R
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsApiService
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsPropertyNetwork
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.toDomainModels
import dev.filipebezerra.app.propertiesfrommars.domain.MarsProperty
import dev.filipebezerra.app.propertiesfrommars.util.event.Event
import dev.filipebezerra.app.propertiesfrommars.util.ext.postEvent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class PropertiesViewModel(private val marsApiService: MarsApiService) : ViewModel() {

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _snackbarTextResource = MutableLiveData<Event<Int>>()
    val snackbarTextResource: LiveData<Event<Int>>
        get() = _snackbarTextResource

    private val _loadStatus = MutableLiveData<MarsApiStatus>()
    val loadStatus: LiveData<MarsApiStatus>
        get() = _loadStatus

    init {
        loadProperties()
    }

    fun tryLoadAgain() = loadProperties()

    private fun loadProperties() {
        viewModelScope.launch {
            _loadStatus.value = MarsApiStatus.LOADING
            try {
                handleGetPropertiesResponse(marsApiService.getProperties())
            } catch (error: HttpException) {
                _loadStatus.value = MarsApiStatus.ERROR
                handleGetPropertiesHttpFailure(error)
            } catch (error: Exception) {
                _loadStatus.value = MarsApiStatus.ERROR
                handleGetPropertiesFailure(error)
            }
        }
    }

    private fun handleGetPropertiesResponse(properties: List<MarsPropertyNetwork>) {
        _properties.value = properties.toDomainModels()
        _loadStatus.value = MarsApiStatus.DONE
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