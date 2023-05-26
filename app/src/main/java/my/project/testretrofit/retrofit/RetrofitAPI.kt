package my.project.testretrofit.retrofit

import my.project.testretrofit.recycler.Product
import my.project.testretrofit.retrofit.RequestBody.RequestBodyProduct
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserLog
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserSign
import my.project.testretrofit.retrofit.ResponseBody.*
import retrofit2.http.*

interface RetrofitAPI {

    @POST("auth/registration")
    suspend fun signUp(@Body body: RequestBodyUserSign) : ResponseToken

    @POST("auth/authentication")
    suspend fun logIn(@Body body: RequestBodyUserLog): ResponseToken

    @GET("user/userdata")
    suspend fun getUserData(@Header("Authorization") token: String): User

    @POST("product/add-product")
    suspend fun addProduct(@Header("Authorization") token: String,
                           @Body body: RequestBodyProduct) : ResponseProduct

    @GET("reviews/reviews-by-id")
    suspend fun getReviewsbyId(@Header("Authorization") token: String,
                               @Query("id") id: Int) : List<ResponseComment>

    @GET("product/get-products-by-user-id")
    suspend fun getProductList(@Header("Authorization") token: String,
                               @Query("id") id: Int) : List<ResponseProduct>


    /*@Headers("Content-Type: application/json")
    @GET("auth/products")
    suspend fun allProducts(@Header("Authorization") token: String) : Products*/
}