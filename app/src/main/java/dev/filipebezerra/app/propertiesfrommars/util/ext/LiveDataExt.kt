package dev.filipebezerra.app.propertiesfrommars.util.ext

import androidx.lifecycle.MutableLiveData
import dev.filipebezerra.app.propertiesfrommars.util.event.Event

fun <T> MutableLiveData<Event<T>>.postEvent(content: T) {
    postValue(Event(content))
}