package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.project.testretrofit.databinding.FragmentNotValidUserBinding

class FragmentNotValid: FragmentBase() {
    private lateinit var binding: FragmentNotValidUserBinding

    companion object {
        fun newInstance(): FragmentNotValid {
            return FragmentNotValid()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotValidUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.update.setOnClickListener {
            openCabinet()
        }
    }

}