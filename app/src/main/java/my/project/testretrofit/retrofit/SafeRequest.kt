package my.project.testretrofit.retrofit

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import my.project.testretrofit.retrofit.ResponseBody.BaseResponseInterface

class SafeRequest(private val lifecycleCoroutineScope: LifecycleCoroutineScope) {
    interface Protection {
        suspend fun makeRequest() : BaseResponseInterface?

        fun ifSuccess(response: BaseResponseInterface?)

        fun ifException() {}
        fun ifConnectionException() {}
        fun ifBackendException() {}
        fun ifAuthException() {}
        fun always() {}
    }

    private var response: BaseResponseInterface? = null

    fun request (protection: Protection) {
        lifecycleCoroutineScope.launch {
            try {
                response = protection.makeRequest()
                protection.ifSuccess(response)
            } catch (e: ConnectionException) {
                Log.e("", "Error!", e)
                protection.ifConnectionException()
            } catch (e: BackendException) {
                Log.e("", "Error!", e)
                if (e.code == 401) {
                    protection.ifAuthException()
                } else {
                    protection.ifBackendException()
                }
            } catch (e: AuthException) {
                Log.e("", "Error!", e)
                protection.ifAuthException()
            } catch (e: Exception) {
                Log.e("", "Error!", e)
                protection.ifException()
            }
            protection.always()
        }

    }
}