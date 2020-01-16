package com.example.tsuandroid.room.repositories

import android.database.Observable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.tsuandroid.room.dao.ElementDAO
import com.example.tsuandroid.room.entity.Element

class ElementRepository(private val elementDAO: ElementDAO) {
    var allElements: LiveData<List<Element>> = elementDAO.getAllElements()
    var count: LiveData<Int> = elementDAO.getCountElements()


    @WorkerThread
    suspend fun insertElement(element: Element) {
        elementDAO.insert(element)
    }

    @WorkerThread
    suspend fun checkElement(name: String): Boolean {
        return elementDAO.checkElement(name)
    }

    @WorkerThread
    suspend fun updateRating(id: Long, rating: Float) {
        elementDAO.updateRating(id, rating)
    }

    /*@WorkerThread
    suspend fun getElementById(id: Long): LiveData<Element> {
        return elementDAO.getElementById(id)
    }*/
}