package com.rabama.practice05.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.rabama.practice05.database.entities.ProductEntity
import com.rabama.practice05.databinding.DialogBinding
import com.rabama.practice05.viewmodel.ProductViewModel


class AddItemFragment: DialogFragment() {

    private var _binding: DialogBinding? = null
    // This property is only valid between onCreateDialog and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel
    lateinit var recyclerView: RecyclerView
    var productList: MutableList<ProductEntity> = mutableListOf()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        _binding = DialogBinding.inflate(LayoutInflater.from(context))
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
/*
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        productViewModel.getAllProducts()

        productViewModel.updateProductLD.observe(this){
            if(it == null){
                showMessage("Error updating item")
            }
        }

        productViewModel.insertProductLD.observe(this){
            productList.add(it)
            recyclerView.adapter?.notifyItemInserted(productList.size)
        }
 */

        binding.btnAdd.setOnClickListener{
            val isProduct = binding.etProductName.text.isNotEmpty()
            val isPrice = binding.etPrice.text.toString().isNotEmpty()
            val isQuantity = binding.etQuantity.text.toString().isNotEmpty()

            if(isProduct && isPrice && isQuantity) {
                productViewModel.addProduct(
                    binding.etProductName.text.toString(),
                    binding.etPrice.text.toString().toDouble(),
                    binding.etQuantity.text.toString().toInt()
                )
            }
            dismiss() //Close the dialog
        }



    }

    /* SHOW MESSAGE */
    private fun showMessage(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }

}