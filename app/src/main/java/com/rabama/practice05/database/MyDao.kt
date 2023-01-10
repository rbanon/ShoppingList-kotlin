package com.rabama.practice05.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.rabama.practice05.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    fun getAllProducts(): MutableList<ProductEntity>

    fun addProduct(productEntity: ProductEntity):Long //Id of the new product

    fun getProductById(id: Long): ProductEntity

    fun updateProduct(productEntity: ProductEntity):Int //Number of affected rows

    fun deleteProduct(productEntity: ProductEntity):Int //Number of affected rows
}