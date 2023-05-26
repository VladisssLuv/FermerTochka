package my.project.testretrofit.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import my.project.testretrofit.R
import my.project.testretrofit.databinding.ItemProductBinding
import my.project.testretrofit.retrofit.ResponseBody.ResponseComment


class RecycleAdapter(): RecyclerView.Adapter<RecycleAdapter.ItemViewHolder>(){

    var products: List<ResponseComment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            title.text = product.nameUser
            brand.text = product.reviews
            for (i in 1 .. product.assessment.toInt()) {
                val imageView = ImageView(root.context)
                imageView.setImageResource(R.drawable.star)
                holder.binding.stars.addView(imageView)
            }
        }
        holder.itemView.tag = product
    }

    class ItemViewHolder(
        val binding: ItemProductBinding
    ): RecyclerView.ViewHolder(binding.root)

}