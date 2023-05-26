package my.project.testretrofit.retrofit.ResponseBody

import java.io.File
import java.util.Base64

data class ResponseProduct(
    val id: Int,
    val id_user: Int,
    val id_category: Int,
    val categoryName: String?,
    val name: String,
    val description: String,
    val cost: Double,
    val photo: String?
) :BaseResponseInterface
