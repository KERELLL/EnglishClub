
package ru.rychkovkirill.englishclub.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.rychkovkirill.englishclub.App
import ru.rychkovkirill.englishclub.R
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import ru.rychkovkirill.englishclub.ui.user.MainActivity
import ru.rychkovkirill.englishclub.ui.user.auth.AuthActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    private val component by lazy{
        App.appComponent
    }

    override fun attachBaseContext(newBase: Context?) {
        component.inject(this)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()

//        if(savedInstanceState == null){
//            if(authRepository.getUser() == null){
//                val intent = Intent(this, AuthActivity::class.java)
//                startActivity(intent)
//                finish()
//            }else{
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

    }
}