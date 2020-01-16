package com.example.tsuandroid.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity
class Element constructor(@PrimaryKey(autoGenerate = true) var id: Long?,
              var name: String,
              var description: String,
              var icon: String,
              var rating: Float
) {
    constructor():this(null,"", "", "", 0.0f)
}