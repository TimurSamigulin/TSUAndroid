package com.example.tsuandroid.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tsuandroid.room.entity.Element


@Dao
interface ElementDAO {
    @Insert
    suspend fun insert(element: Element)

    @Update
    suspend fun updateElement(element: Element)

    @Query("SELECT COUNT(*) FROM Element")
    fun getCountElements(): LiveData<Int>

    @Query("SELECT * FROM element")
    fun getAllElements(): LiveData<List<Element>>

    @Query("UPDATE element SET rating = :rating WHERE id = :id")
    suspend fun updateRating(id: Long, rating: Float)

    @Query("SELECT EXISTS(SELECT COUNT(*) FROM Element WHERE name = :name)")
    suspend fun checkElement(name: String): Boolean
}