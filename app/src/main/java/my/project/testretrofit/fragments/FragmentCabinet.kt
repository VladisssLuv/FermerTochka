package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import my.project.testretrofit.R
import my.project.testretrofit.databinding.FragmentCabinetBinding
import my.project.testretrofit.databinding.FragmentChatBinding
import my.project.testretrofit.recycler.ActionListener
import my.project.testretrofit.recycler.ItemRecycler
import my.project.testretrofit.recycler.Product
import my.project.testretrofit.recycler.RecycleAdapter
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import my.project.testretrofit.retrofit.ResponseBody.User
import my.project.testretrofit.retrofit.RetrofitSource
import my.project.testretrofit.retrofit.SafeRequest
import okhttp3.ResponseBody

class FragmentCabinet: FragmentBase() {
    private lateinit var binding: FragmentCabinetBinding
    private lateinit var adapter: RecycleAdapter
    private val retrofitSource: RetrofitSource = RetrofitSource()

    companion object {
         fun newInstance(): FragmentCabinet {
            return FragmentCabinet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCabinetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = arrayListOf<Product>()
        SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
            override suspend fun makeRequest(): BaseResponseInterface? {
                return retrofitSource.getUserData()
            }

            override fun ifSuccess(response: BaseResponseInterface?) {
                response as User
                binding.name.text = response.name
                binding.role.text = response.role
            }
        })

        adapter = RecycleAdapter(object : ActionListener {
            override fun onClick(v: View) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_click)
                v.startAnimation(animation)
                openChat()
            }
        })
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = linearLayoutManager
        binding.recycler.adapter = adapter

    }
}