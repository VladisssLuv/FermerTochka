package my.project.testretrofit.retrofit.RequestBody

import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface
import java.io.File
import java.util.Base64

data class RequestBodyProduct(
    val id_user: Int,
    val id_category: Int,
    val name: String,
    val description: String,
    val cost: Double,
    val photo: String?
) : BaseResponseInterface
