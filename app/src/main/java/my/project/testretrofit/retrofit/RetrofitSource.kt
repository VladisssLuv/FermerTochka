package my.project.testretrofit.retrofit

import my.project.testretrofit.TokenStorage
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserLog
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserSign
import my.project.testretrofit.retrofit.ResponseBody.ResponseToken
import my.project.testretrofit.retrofit.ResponseBody.User

class RetrofitSource(): BaseRetrofit() {
    private lateinit var managerRetrofitAPI: RetrofitAPI

    init {
        initManagerRetrofitAPI()
    }

    private fun initManagerRetrofitAPI() {
        managerRetrofitAPI = super.emptyRetrofit.create(RetrofitAPI::class.java)
    }

    suspend fun signUp(body: RequestBodyUserSign) : ResponseToken = super.wrapRetrofitExceptions {
        managerRetrofitAPI.signUp(body)
    }

    suspend fun logIn(body: RequestBodyUserLog) : ResponseToken = super.wrapRetrofitExceptions {
        managerRetrofitAPI.logIn(body)
    }

    suspend fun getUserData(): User = super.wrapRetrofitExceptions {
        println("DDDDDDD Bearer " + TokenStorage.TOKEN)
        managerRetrofitAPI.getUserData("Bearer " + TokenStorage.TOKEN)
    }

   /* suspend fun allProducts() : Products = super.wrapRetrofitExceptions {
        managerRetrofitAPI.allProducts(TokenStorage.TOKEN ?: "")
    }*/
}