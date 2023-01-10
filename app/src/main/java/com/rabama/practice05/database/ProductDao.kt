package com.rabama.practice05.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rabama.practice05.database.entities.ProductEntity

@Dao
interface ProductDao:MyDao {
    @Query("SELECT * FROM product_entity")
    override fun getAllProducts(): MutableList<ProductEntity>

    @Insert
    override fun addProduct(productEntity: ProductEntity):Long //Id of the new  task

    @Insert
    fun insertAll(productEntity: ProductEntity) // TODO: (Backup)

    @Query("SELECT * FROM product_entity WHERE id LIKE :id")
    override fun getProductById(id: Long): ProductEntity

    @Update
    override fun updateProduct(productEntity: ProductEntity):Int //Number of affected rows

    @Delete
    override fun deleteProduct(productEntity: ProductEntity):Int //Number of affected rows

}