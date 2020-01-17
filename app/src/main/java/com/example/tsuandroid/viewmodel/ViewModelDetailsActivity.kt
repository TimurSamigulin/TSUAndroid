package com.example.tsuandroid.viewmodel

import android.app.Application
import android.media.Rating
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tsuandroid.room.database.ElementDatabase
import com.example.tsuandroid.room.entity.Element
import com.example.tsuandroid.room.repositories.ElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModelDetailsActivity(application: Application): AndroidViewModel(application) {
    private var repository: ElementRepository
    lateinit var element: LiveData<Element>

    init {
        val elementDao = ElementDatabase.getInstance(application,viewModelScope).elementDAO()
        repository = ElementRepository(elementDao)
    }

    fun updateRating(id: Long, rating: Float) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRating(id, rating)
    }
}