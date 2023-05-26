package my.project.testretrofit.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.project.testretrofit.databinding.FragmentChatBinding
import my.project.testretrofit.databinding.FragmentSignInBinding

class FragmentChat: Fragment() {
    private lateinit var binding: FragmentChatBinding

    companion object {
        fun newInstance(): FragmentChat {
            return FragmentChat()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}