package com.rabama.practice05.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "product_entity")
data class ProductEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String = "",
    var quantity:Int = 0,
    var unitaryPrice:Double = 1.0
)

