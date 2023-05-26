package my.project.testretrofit.retrofit.ResponseBody

data class User(
    val id: Int,
    val login: String,
    val name : String,
    val email : String,
    val phone : String,
    val dateBirth: String,
    val valid: Boolean
) : BaseResponseInterface
