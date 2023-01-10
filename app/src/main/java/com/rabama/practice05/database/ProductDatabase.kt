package com.rabama.practice05.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rabama.practice05.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase:RoomDatabase() {
    abstract fun productDao():ProductDao

    companion object{ //Singleton Pattern
        private var instance:ProductDao? = null

        fun getInstance(context: Context):ProductDao{
            return instance ?: Room.databaseBuilder(context, ProductDatabase::class.java, "products-db")
                    .build()
                    .productDao().also { instance = it }
        }
    }
}