package com.example.tsuandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tsuandroid.retrofit.ApiElement
import com.example.tsuandroid.retrofit.models.ElementFromApi
import com.example.tsuandroid.room.database.ElementDatabase
import com.example.tsuandroid.room.entity.Element
import com.example.tsuandroid.room.repositories.ElementRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class ViewModelMainActivity(application: Application): AndroidViewModel(application) {
    private var repository: ElementRepository
    var allElements : LiveData<List<Element>>
    val count: LiveData<Int>

    init {
        val elementDao = ElementDatabase.getInstance(application,viewModelScope).elementDAO()
        repository = ElementRepository(elementDao)

        allElements = repository.allElements
        count = repository.count

        val apiService = ApiElement()
        GlobalScope.launch(Dispatchers.Main) {
            val currentElements = apiService.getElements().await()
            for(currentElement in currentElements) {
                checkElement(currentElement)
            }
        }

    }

    fun insert(element: Element) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertElement(element)
    }

    fun checkElement(currentElement: ElementFromApi) = viewModelScope.launch(Dispatchers.IO) {
        insert(Element(id = null, name = currentElement.name, description = currentElement.description, icon = currentElement.icon, rating = 0f))
        if(!repository.checkElement(currentElement.name)){
            Log.d("1", currentElement.name)
            insert(Element(id = null, name = currentElement.name, description = currentElement.description, icon = currentElement.icon, rating = 0f))
        }
    }

}