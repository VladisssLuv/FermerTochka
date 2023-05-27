package my.project.testretrofit.retrofit

import my.project.testretrofit.TokenStorage
import my.project.testretrofit.retrofit.RequestBody.RequestBodyProduct
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserLog
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserSign
import my.project.testretrofit.retrofit.ResponseBody.*

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
        managerRetrofitAPI.getUserData("Bearer " + TokenStorage.TOKEN)
    }

    suspend fun addProduct(body: RequestBodyProduct): ResponseProduct = super.wrapRetrofitExceptions {
        managerRetrofitAPI.addProduct("Bearer " + TokenStorage.TOKEN, body)
    }

    suspend fun getReviewsbyId(id: Int) : List<ResponseComment> = super.wrapRetrofitExceptions  {
        managerRetrofitAPI.getReviewsbyId("Bearer " + TokenStorage.TOKEN, id)
    }
    suspend fun getProductList(id: Int) : List<ResponseProduct> = super.wrapRetrofitExceptions  {
        managerRetrofitAPI.getProductList("Bearer " + TokenStorage.TOKEN, id)
    }

    suspend fun getProductList(id: Int, idCategory: Int ) : List<ResponseProduct> = super.wrapRetrofitExceptions  {
        managerRetrofitAPI.getProductList("Bearer " + TokenStorage.TOKEN, id, idCategory)
    }

}