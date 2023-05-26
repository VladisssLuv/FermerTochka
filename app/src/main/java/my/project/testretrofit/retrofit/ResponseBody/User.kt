package my.project.testretrofit.retrofit.ResponseBody

import java.io.File
import java.util.Date

data class User(
    val id: Int,
    val login: String,
    val name : String,
    val email : String,
    val phone : String,
    val address: String,
    val dateRegistration: Any?,
    val photo: String?,
    val role: String
) : BaseResponseInterface
