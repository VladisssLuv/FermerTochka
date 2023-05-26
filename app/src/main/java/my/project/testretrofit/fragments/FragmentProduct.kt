package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.project.testretrofit.databinding.FragmentPoductBinding

class FragmentProduct: FragmentBase() {
    private lateinit var binding: FragmentPoductBinding
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

        binding.addProduct.setOnClickListener {
            openAddProduct()
        }
    }
}