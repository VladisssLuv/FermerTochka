package my.project.testretrofit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.project.testretrofit.databinding.ActivityMainBinding
import my.project.testretrofit.fragments.*
import my.project.testretrofit.utils.MyWebSocketClientKT


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    val client: MyWebSocketClientKT = MyWebSocketClientKT("ws://192.168.6.38:8001")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("myCache", Context.MODE_PRIVATE)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            var resultFlag = false
            when(item.itemId) {
                R.id.item_1 -> {
                    if (FragmentBase().safeCheckUserValid())
                        openChat() else  openNotValid()
                    resultFlag = true
                }
                R.id.item_2 -> {
                    if (FragmentBase().safeCheckUserValid())
                        openChat() else  openNotValid()
                    resultFlag = true
                }
                R.id.item_3 -> {
                    if (FragmentBase().safeCheckUserValid())
                        openProduct() else  openNotValid()
                    resultFlag = true
                }
                R.id.item_4 -> {
                    if (FragmentBase().safeCheckUserValid())
                        openList() else  openNotValid()
                    resultFlag = true
                }

            }
            resultFlag
        }
        client.connect()
        loadTokenFromCache()
        inAuthOrListIfUserNotAuthorized()
    }

    private fun inAuthOrListIfUserNotAuthorized() {
        if (tokenIsNotNull()) {
            openChat()
        } else {
            openLogIn()
        }
    }

    private fun loadTokenFromCache(){
        TokenStorage.TOKEN = sharedPref.getString(Constants.TOKEN_NAME_CAHCE, null).toString()
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

    private fun openList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentList.newInstance())
            .commit()
    }

    private fun openNotValid() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentNotValid.newInstance())
            .commit()
    }

    private fun openProduct() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentProdct.newInstance())
            .commit()
    }

    public fun goIngProduct() {
        binding.bottomNavigation.selectedItemId = R.id.item_1
    }
}