package my.project.testretrofit.retrofit.ResponseBody

data class ResponseComment(
    val id: Int,
    val user: Int,
    val farmer: Int,
    val nameUser : String,
    val reviews: String,
    val assessment : String
): BaseResponseInterface
