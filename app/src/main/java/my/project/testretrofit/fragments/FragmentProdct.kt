package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import my.project.testretrofit.R
import my.project.testretrofit.databinding.FragmentNotValidUserBinding
import my.project.testretrofit.databinding.FragmentProductBinding
import my.project.testretrofit.recycler.ActionListener
import my.project.testretrofit.recycler.ItemRecycler
import my.project.testretrofit.recycler.RecycleAdapter
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import my.project.testretrofit.retrofit.SafeRequest

class FragmentProdct: FragmentBase() {
    private lateinit var binding: FragmentProductBinding
    private lateinit var adapter: RecycleAdapter

    companion object {
        fun newInstance(): FragmentProdct {
            return FragmentProdct()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProduct()

        adapter = RecycleAdapter(object : ActionListener {
            override fun onClick(v: View) {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_click)
                v.startAnimation(animation)
                openChat()
            }
        })

    }

    private fun getProduct() {
        SafeRequest(lifecycleScope).request(object : SafeRequest.Protection{
            override suspend fun makeRequest(): BaseResponseInterface? {
                return null//пока что
            }

            override fun ifSuccess(response: BaseResponseInterface?) {

            }

            override fun ifException() {
                showToast("")
            }
        })
    }
}