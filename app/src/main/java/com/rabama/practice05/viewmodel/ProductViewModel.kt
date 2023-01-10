package com.rabama.practice05.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rabama.practice05.database.MyDao
import com.rabama.practice05.database.ProductDatabase
import com.rabama.practice05.database.entities.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    // Implement Dao pattern
    val myDao: MyDao = ProductDatabase.getInstance(context)

    val productListLD:MutableLiveData<MutableList<ProductEntity>> = MutableLiveData()
    val updateProductLD:MutableLiveData<ProductEntity?> = MutableLiveData()
    val deleteProductLD:MutableLiveData<Int> = MutableLiveData()
    val insertProductLD:MutableLiveData<ProductEntity> = MutableLiveData()

    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            productListLD.postValue(myDao.getAllProducts())
        }
    }

    fun addProduct(name:String, price:Double, quantity:Int){
        viewModelScope.launch(Dispatchers.IO){
            val id = myDao.addProduct(ProductEntity(name = name, unitaryPrice = price, quantity = quantity ))
            val recoveryTask = myDao.getProductById(id)
            insertProductLD.postValue(recoveryTask)
        }
    }

    fun deleteProduct(product:ProductEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val res = myDao.deleteProduct(product)
            if(res>0)
                deleteProductLD.postValue(product.id)
            else{
                deleteProductLD.postValue(-1)
            }
        }
    }

    fun updateProduct(product:ProductEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val res = myDao.updateProduct(product)
            if(res>0) updateProductLD.postValue(product)
            else updateProductLD.postValue(null)
        }
    }

    /* Backup methods */

}