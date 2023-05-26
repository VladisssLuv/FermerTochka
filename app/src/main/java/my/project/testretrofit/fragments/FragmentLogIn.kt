package my.project.testretrofit.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import my.project.testretrofit.*
import my.project.testretrofit.databinding.FragmentLogInBinding
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserLog
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import my.project.testretrofit.retrofit.ResponseBody.ResponseToken
import my.project.testretrofit.retrofit.RetrofitSource
import my.project.testretrofit.retrofit.SafeRequest
import my.project.testretrofit.utils.Validator

class FragmentLogIn : FragmentBase() {
    private lateinit var binding: FragmentLogInBinding
    private val validator: Validator = Validator()
    private val retrofitSource: RetrofitSource = RetrofitSource()

    private val CHANNEL_ID = "your_channel_id"
    private val NOTIFICATION_ID = 1

    companion object {
        fun newInstance(): FragmentLogIn {
            return FragmentLogIn()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logInButton.setOnClickListener {
            tryLogIn()
            /*val intent = Intent(requireContext(), NotificationService::class.java)
            requireContext().startService(intent)
            requireContext().stopService(intent)*/

            /*val mainActivity = activity as MainActivity
            println("DDDDD " + mainActivity.client.isOpen)
            mainActivity.client.send("{\"event\":\"message\",\"data\":\"Hello, server!\"}")*/
        }

        binding.textReg.setOnClickListener {
            openSignIn()
        }
    }

    private fun tryLogIn() {
        val username: String = binding.editTextName.text.toString()
        val password: String = binding.editTextPassword.text.toString()
        val flagValidPassword: Boolean = isPasswordValid(password)
        val flagValidUsername: Boolean = isUsernameValid(username)

        updateWarningTextInputByFlag(
            flagValidPassword,
            binding.inputPassword,
            validator.getPasswordRequirements())

        updateWarningTextInputByFlag(
            flagValidUsername,
            binding.inputName,
            validator.getUsernameRequirements())

        if (/*flagValidPassword*/true && flagValidUsername) {
            safeLogIn(username, password)
        }
    }

    private fun safeLogIn(username: String, password: String) {
        SafeRequest(lifecycleScope).request(object : SafeRequest.Protection {
            override suspend fun makeRequest(): BaseResponseInterface {
                binding.progressBar.visibility = View.VISIBLE
                return retrofitSource.logIn(
                    RequestBodyUserLog(
                        username,
                        password )
                )
            }

            override fun ifSuccess(response: BaseResponseInterface?) {
                if (response != null && response is ResponseToken) {
                    showToast("Вы авторизовались")
                    saveToken(response.token)
                    println(response.token)
                    openList()
                }
            }

            override fun always() {
                binding.progressBar.visibility = View.GONE
            }

            override fun ifConnectionException() {
                showToast("Нет подключения к интернету")
            }

            // тут должен быть ifAuthException но сервер не возвращает 402, только 400
            override fun ifBackendException() {
                showToast("Не верный логин или пароль")
            }
        })
    }

    private fun isPasswordValid(password: String): Boolean {
        return validator.validatePassword(password)
    }

    private fun isUsernameValid(username: String): Boolean {
        return validator.validateUsername(username)
    }

    private fun updateWarningTextInputByFlag(
        flagWarning: Boolean,
        textInputLayout: TextInputLayout,
        message: String
    ) {
        if (!flagWarning) {
            textInputLayout.helperText = message
        } else {
            textInputLayout.helperText = ""
        }
    }
}