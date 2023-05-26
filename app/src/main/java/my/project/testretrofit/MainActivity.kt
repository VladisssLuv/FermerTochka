package my.project.testretrofit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import my.project.testretrofit.databinding.ActivityMainBinding
import my.project.testretrofit.fragments.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("myCache", Context.MODE_PRIVATE)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            var resultFlag = false
            var fragment = FragmentBase()

            loadTokenFromCache()

            when(item.itemId) {
                R.id.item_1 -> {
                    fragment = if (tokenIsNotNull())
                        FragmentCabinet.newInstance() else FragmentLogIn.newInstance()
                    resultFlag = true
                }
                R.id.item_2 -> {
                    fragment = if(tokenIsNotNull()) FragmentProduct.newInstance()
                    else FragmentLogIn.newInstance()
                    resultFlag = true
                }

            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            if (!resultFlag) {
                binding.bottomNavigation.selectedItemId = R.id.item_1
            }
            resultFlag
        }
        binding.bottomNavigation.selectedItemId = R.id.item_1
    }


    private fun loadTokenFromCache(){
        TokenStorage.TOKEN = sharedPref.getString(Constants.TOKEN_NAME_CAHCE, null)
        println("DDDDD " + TokenStorage.TOKEN)
    }

    private fun tokenIsNotNull(): Boolean {
        return TokenStorage.TOKEN != null
    }


    private fun openLogIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentLogIn.newInstance())
            .commit()
    }

    private fun openChat() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentChat.newInstance())
            .commit()
    }
    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}