package my.project.testretrofit.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.project.testretrofit.databinding.ItemProductBinding
import my.project.testretrofit.retrofit.ResponseBody.ResponseComment

interface ActionListener {
    fun onClick(v: View)
}

class RecycleAdapter(
    private val actionListener: ActionListener
): RecyclerView.Adapter<RecycleAdapter.ItemViewHolder>(), View.OnClickListener {

    var products: List<ResponseComment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = products[position]
        with(holder.binding) {
            title.text = product.nameUser
            brand.text = product.reviews
            price.text = product.assessment
        }
        holder.itemView.tag = product
    }

    class ItemViewHolder(
        val binding: ItemProductBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        actionListener.onClick(v)
    }

}