package my.project.testretrofit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.project.testretrofit.databinding.FragmentCabinetBinding
import my.project.testretrofit.databinding.FragmentChatBinding

class FragmentCabinet: FragmentBase() {
    private lateinit var binding: FragmentCabinetBinding

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
    }
}