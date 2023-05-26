package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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


        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = linearLayoutManager

        adapter = RecycleAdapterProduct(object : ActionListener {
            override fun onClick(v: View) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_click)
                v.startAnimation(animation)
                openChat()
            }
        })
        binding.recycler.adapter = adapter

        var array = listOf<ResponseProduct>()

        SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
            override suspend fun makeRequest(): BaseResponseInterface? {
                return ResponseProductList(
                    retrofitSource.getProductList(TokenStorage.ID)
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

        binding.addProduct.setOnClickListener {
            openAddProduct()
        }
    }
}