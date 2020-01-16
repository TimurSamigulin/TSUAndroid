package com.example.tsuandroid.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tsuandroid.room.dao.ElementDAO
import com.example.tsuandroid.room.entity.Element
import kotlinx.coroutines.CoroutineScope


@Database(entities = [(Element::class)], version = 1)
abstract class ElementDatabase : RoomDatabase() {
    abstract fun elementDAO(): ElementDAO

    companion object {
        @Volatile
        private var INSTANCE: ElementDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): ElementDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ElementDatabase::class.java,
                    "element.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }
}