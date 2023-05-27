package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import my.project.testretrofit.R
import my.project.testretrofit.TokenStorage
import my.project.testretrofit.databinding.FragmentPoductBinding
import my.project.testretrofit.recycler.ActionListener
import my.project.testretrofit.recycler.RecycleAdapter
import my.project.testretrofit.recycler.RecycleAdapterProduct
import my.project.testretrofit.retrofit.ResponseBody.*
import my.project.testretrofit.retrofit.RetrofitSource
import my.project.testretrofit.retrofit.SafeRequest

class FragmentProduct: FragmentBase() {
    private lateinit var binding: FragmentPoductBinding
    private lateinit var adapter: RecycleAdapterProduct
    private val retrofitSource: RetrofitSource = RetrofitSource()

    companion object {
        fun newInstance(): FragmentProduct {
            return FragmentProduct()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPoductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (TokenStorage.ID < 0) {
            openLogIn()
        }

        val spinner: Spinner = binding.basketTitle
        val items = arrayOf("Без фильтра", "Все", "Молочная продукция", "Мясная продукция", "Овощи", "Фрукты")
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter2

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                println("DDDDDDDDDDDDDDDDDDDDDD " + position)
                binding.progressBarList.visibility = View.VISIBLE
                safeRequest(position + 1)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        safeRequest(1)

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = linearLayoutManager

        adapter = RecycleAdapterProduct(object : ActionListener {
            override fun onClick(v: View) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_click)
                v.startAnimation(animation)
            }
        })
        binding.recycler.adapter = adapter


        binding.addProduct.setOnClickListener {
            openAddProduct()
        }
    }

    private fun safeRequest(idCategory : Int) {
        var array = listOf<ResponseProduct>()

        SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
            override suspend fun makeRequest(): BaseResponseInterface? {
                return ResponseProductList(
                    if (idCategory == 1)
                        retrofitSource.getProductList(TokenStorage.ID)
                    else retrofitSource.getProductList(TokenStorage.ID, idCategory)
                )
            }

            override fun ifSuccess(response: BaseResponseInterface?) {
                println("DDDDDDD " + (response as ResponseProductList).list)
                array = (response as ResponseProductList).list
                adapter.products = array
                binding.progressBarList.visibility = View.INVISIBLE
            }

            override fun ifException() {
                println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR ")
            }
        })
    }
}