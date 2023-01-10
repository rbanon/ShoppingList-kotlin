package com.rabama.practice05.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rabama.practice05.R
import com.rabama.practice05.adapters.ProductAdapter
import com.rabama.practice05.database.entities.ProductEntity
import com.rabama.practice05.databinding.ActivityMainBinding
import com.rabama.practice05.extensions.format
import com.rabama.practice05.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    var productList: MutableList<ProductEntity> = mutableListOf()

    lateinit var fab: FloatingActionButton

    private lateinit var productViewModel: ProductViewModel

    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
        }.root)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        productViewModel.getAllProducts()

        productViewModel.productListLD.observe(this){
            productList.clear()
            productList.addAll(it)
            updateTotal()
            recyclerView.adapter?.notifyDataSetChanged()
        }

        productViewModel.updateProductLD.observe(this){
            if(it == null){
                showMessage("Error updating product")
            } else {
                updateTotal()
            }
        }

        productViewModel.deleteProductLD.observe(this){ id ->
            if(id != -1){
                val product = productList.filter {
                    it.id == id
                }[0]
                val pos = productList.indexOf(product)
                productList.removeAt(pos)
                recyclerView.adapter?.notifyItemRemoved(pos)
                updateTotal()
            }else{
                showMessage("Error deleting product")
            }
        }

        productViewModel.insertProductLD.observe(this){
            productList.add(it)
            recyclerView.adapter?.notifyItemInserted(productList.size)
        }

        setUpRecyclerView()

        // Manage floating action button
        fab = binding.fab
        fab.setOnClickListener { view ->
            Snackbar.make(view, "FAB Clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
            openDialog()
        }
    }

    private fun openDialog() {
        AddItemFragment().show(supportFragmentManager, "AddItemFragment")
    }

    private fun setUpRecyclerView() {
        adapter = ProductAdapter(
            productList,
            { productEntity ->  updateProduct(productEntity) },
            { productEntity -> deleteProduct(productEntity) }
        )
        recyclerView = binding.rvProduct
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun updateProduct(productEntity: ProductEntity) {
        productViewModel.updateProduct(productEntity)
    }

    private fun deleteProduct(productEntity: ProductEntity) {
        productViewModel.deleteProduct(productEntity)

    }

    override fun onCreateOptionsMenu (menu: Menu?): Boolean {
        menuInflater.inflate (R.menu.my_menu, menu)
        return true
    }

    // Manage options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.backup_settings -> {
                Snackbar.make(binding.root, "Backup settings Clicked", Snackbar.LENGTH_SHORT)
                    .show()
            }
            R.id.delete_settings -> {
                val q = productList.size
                for(product in productList){
                    deleteProduct(product)
                }
                Snackbar.make(binding.root, "$q products deleted.", Snackbar.LENGTH_SHORT)
                    .show()

            }
            R.id.restore_settings -> {
                Snackbar.make(binding.root, "Restore settings Clicked", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /* HIDE KEYBOARD */
    private fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    /* SHOW MESSAGE */
    private fun showMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    /* UPDATE TOTAL PRICE */ // TODO: (Revisar función)
    private fun updateTotal(){
        var total = 0.0
        for(product in productList){
            total += product.unitaryPrice * product.quantity
        }
        binding.tvTotal.text = total.format(2)+ " €"
    }
}