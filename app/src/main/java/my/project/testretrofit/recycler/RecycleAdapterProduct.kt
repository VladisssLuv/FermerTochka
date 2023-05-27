package my.project.testretrofit.recycler

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import my.project.testretrofit.R
import my.project.testretrofit.databinding.ItemProductBinding
import my.project.testretrofit.databinding.ItemProductFarmerBinding
import my.project.testretrofit.retrofit.ResponseBody.ResponseComment
import my.project.testretrofit.retrofit.ResponseBody.ResponseProduct

interface ActionListener {
    fun onClick(v: View)
}

class RecycleAdapterProduct(
    private val actionListener: ActionListener
): RecyclerView.Adapter<RecycleAdapterProduct.ItemViewHolder>(), View.OnClickListener {

    var products: List<ResponseProduct> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductFarmerBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            title.text = product.name
            brand.text = product.categoryName
            ct.text = product.cost.toString() + " руб."
            desc.text = product.description

            if (product.photo != null) {
                val decodedString = Base64.decode(product.photo, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                img.setImageBitmap(decodedByte)
            } else {
                img.setImageDrawable(null)
            }

        }
        holder.itemView.tag = product
    }

    class ItemViewHolder(
        val binding: ItemProductFarmerBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        actionListener.onClick(v)
    }

}