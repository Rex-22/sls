package sls.ruben.strexleadsystem.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sls.ruben.strexleadsystem.R
import android.content.Intent



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this@SplashActivity, LoginViewActivity::class.java))

        // Simulate startup
        Thread.sleep(2000)

        finish()
    }
}
