package dev.filipebezerra.app.propertiesfrommars.properties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.filipebezerra.app.propertiesfrommars.R
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsApiService
import dev.filipebezerra.app.propertiesfrommars.datasource.remote.MarsPropertyNetwork
import dev.filipebezerra.app.propertiesfrommars.util.event.Event
import dev.filipebezerra.app.propertiesfrommars.util.ext.postEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        marsApiService.getProperties().enqueue(object : Callback<List<MarsPropertyNetwork>> {
            override fun onResponse(
                call: Call<List<MarsPropertyNetwork>>,
                response: Response<List<MarsPropertyNetwork>>
            ) {
                handleGetPropertiesResponse(response)
            }

            override fun onFailure(
                call: Call<List<MarsPropertyNetwork>>,
                t: Throwable
            ) {
                handleGetPropertiesFailure(t)
            }
        })
    }

    private fun handleGetPropertiesResponse(response: Response<List<MarsPropertyNetwork>>) {
        if (response.isSuccessful) {
            _properties.value = "We got ${response.body()?.size ?: 0} properties available right now!"
        } else {
            _snackbarTextResource.postEvent(R.string.unsuccessful_get_properties_response)
            _properties.value = response.errorBody().toString()
        }
    }

    private fun handleGetPropertiesFailure(error: Throwable) {
        Timber.e(error, "Something does wrong trying to load properties")
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