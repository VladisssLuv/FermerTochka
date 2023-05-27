package my.project.testretrofit.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import my.project.testretrofit.Constants
import my.project.testretrofit.R
import my.project.testretrofit.TokenStorage
import my.project.testretrofit.databinding.FragmentListBinding
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import my.project.testretrofit.retrofit.ResponseBody.User
import my.project.testretrofit.retrofit.RetrofitSource
import my.project.testretrofit.retrofit.SafeRequest

open class FragmentBase: Fragment() {

    public fun openLogIn() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentLogIn.newInstance())
            ?.commit()
    }

    public fun openList() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentList.newInstance())
            ?.commit()
    }

    public fun openChat() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentChat.newInstance())
            ?.commit()
    }

    public fun openSignIn() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentSignIn.newInstance())
            ?.commit()
    }

    public fun openNotValidUser() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentNotValid.newInstance())
            ?.commit()
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun openCabinet() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentCabinet.newInstance())
            ?.commit()
    }

    protected fun openAddProduct() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentAddProduct.newInstance())
            ?.commit()
    }
    protected fun saveToken(token: String?, id: Int) {
        println("СОХРАНЕНИЕ " + token)
        activity?.getSharedPreferences("myCache", Context.MODE_PRIVATE)?.edit()
            ?.putString(Constants.TOKEN_NAME_CAHCE, token)
            ?.apply()
        saveID(id)
        TokenStorage.TOKEN = token
    }
    protected fun saveID (id: Int) {
        activity?.getSharedPreferences("myCache", Context.MODE_PRIVATE)?.edit()
            ?.putInt("ID", id)
            ?.apply()
        TokenStorage.ID = id
    }

    protected fun openProductList() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations( R.anim.enter_anim, R.anim.exit_anim)
            ?.replace(R.id.fragment_container, FragmentProduct.newInstance())
            ?.commit()
    }

}