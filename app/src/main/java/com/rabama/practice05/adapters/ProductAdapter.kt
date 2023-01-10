package com.rabama.practice05.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rabama.practice05.R
import com.rabama.practice05.database.entities.ProductEntity
import com.rabama.practice05.extensions.format
import com.rabama.practice05.ui.MainActivity

class ProductAdapter(
    private val productList: List<ProductEntity>,
    private val updateProduct: (ProductEntity) -> Unit,
    private val deleteProduct: (ProductEntity) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productList[position]
        holder.bind(item, updateProduct,deleteProduct)
    }

    override fun getItemCount() = productList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.findViewById<TextView>(R.id.tvProductName)
        private val tvPrice = view.findViewById<TextView>(R.id.tvProductPrice)
        private val tvQuantity = view.findViewById<TextView>(R.id.tvProductQuantity)
        private val tvSubTotal = view.findViewById<TextView>(R.id.tvSubTotal)
        private val btnDelete = view.findViewById<ImageView>(R.id.btnDelete)
        //private val clCard = view.findViewById<ConstraintLayout>(R.id.clCard)

        fun bind(
            product: ProductEntity,
            updateProduct: (ProductEntity) -> Unit,
            deleteProduct: (ProductEntity) -> Unit
        ) {
            tvName.text = product.name
            tvQuantity.text = product.quantity.toString()
            tvPrice.text = product.unitaryPrice.toString()
            var productPrice = product.unitaryPrice*product.quantity
            tvSubTotal.text = productPrice.format(2)

            btnDelete.setOnClickListener {
                if (product.quantity > 1) {
                    product.quantity--
                    updateProduct(product)
                    tvQuantity.text = product.quantity.toString()
                    productPrice = product.unitaryPrice*product.quantity
                    tvSubTotal.text = productPrice.format(2)
                } else {
                    deleteProduct(product)
                }
            }

        }
    }

}