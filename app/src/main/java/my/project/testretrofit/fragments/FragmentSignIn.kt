package my.project.testretrofit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import my.project.testretrofit.R
import my.project.testretrofit.databinding.FragmentLogInBinding
import my.project.testretrofit.databinding.FragmentSignInBinding
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserSign
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import my.project.testretrofit.retrofit.ResponseBody.ResponseToken
import my.project.testretrofit.retrofit.RetrofitSource
import my.project.testretrofit.retrofit.SafeRequest
import my.project.testretrofit.utils.Validator

class FragmentSignIn: FragmentBase() {
    private lateinit var binding: FragmentSignInBinding
    private val validator: Validator = Validator()
    private val retrofitSource: RetrofitSource = RetrofitSource()
    private var sharedPref: SharedPreferences? = null

    companion object {
        fun newInstance(): FragmentSignIn {
            return FragmentSignIn()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = activity?.getSharedPreferences("myCache", Context.MODE_PRIVATE)

        binding.textReg.setOnClickListener {
            openLogIn()
        }

        binding.signInButton.setOnClickListener {
            trySignIn()
        }
    }

    private fun trySignIn() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val login = binding.editTextName.text.toString()

        safeSingUp(email, password, login)
    }

    private fun safeSingUp(email: String, password: String, login: String) {
        SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
            override suspend fun makeRequest(): BaseResponseInterface? {
                return retrofitSource.signUp(RequestBodyUserSign(
                    login, password,"DIMOCHKA", email,
                    "89088908089", "2023-05-22"
                ))
            }

            override fun ifSuccess(response: BaseResponseInterface?) {
                response as ResponseToken
                saveToken(response.token)
                println(response.token)
                openNotValidUser()
            }
        })
    }

}