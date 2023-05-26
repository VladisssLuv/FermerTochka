package my.project.testretrofit.retrofit

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import my.project.testretrofit.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

open class BaseRetrofit {
    private val moshi: Moshi = Moshi.Builder().build()
    private val errorAdapter = moshi.adapter(ErrorResponseBody::class.java)
    protected lateinit var emptyRetrofit: Retrofit

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        emptyRetrofit = Retrofit.Builder()
            .baseUrl(Constants.BASHE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: AppException) {
            throw e
            // moshi
        } catch (e: JsonDataException) {
            throw ParseBackendResponseException(e)
        } catch (e: JsonEncodingException) {
            throw ParseBackendResponseException(e)
            // retrofit
        } catch (e: HttpException) {
            throw createBackendException(e)
            // mostly retrofit
        } catch (e: IOException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        return try {
            val errorBody: ErrorResponseBody = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            BackendException(e.code(), errorBody.status)
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }

    class ErrorResponseBody(
        val status: String
    )
}