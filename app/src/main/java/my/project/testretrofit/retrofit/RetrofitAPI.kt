package my.project.testretrofit.retrofit

import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserLog
import my.project.testretrofit.retrofit.RequestBody.RequestBodyUserSign
import my.project.testretrofit.retrofit.ResponseBody.ResponseToken
import my.project.testretrofit.retrofit.ResponseBody.User
import retrofit2.http.*

interface RetrofitAPI {

    @POST("auth/registration")
    suspend fun signUp(@Body body: RequestBodyUserSign) : ResponseToken

    @POST("auth/authentication")
    suspend fun logIn(@Body body: RequestBodyUserLog): ResponseToken

    @GET("user/userdata")
    suspend fun getUserData(@Header("Authorization") token: String): User


    /*@Headers("Content-Type: application/json")
    @GET("auth/products")
    suspend fun allProducts(@Header("Authorization") token: String) : Products*/
}