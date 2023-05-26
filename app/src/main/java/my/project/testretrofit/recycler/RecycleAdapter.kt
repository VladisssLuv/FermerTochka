package my.project.testretrofit.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.project.testretrofit.databinding.ItemProductBinding

interface ActionListener {
    fun onClick(v: View)
}

class RecycleAdapter(
    private val actionListener: ActionListener
): RecyclerView.Adapter<RecycleAdapter.ItemViewHolder>(), View.OnClickListener {

    var products: List<ItemRecycler> = emptyList()
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
            title.text = "ItemRecycler_title"
            brand.text = "ItemRecycler_brand"
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