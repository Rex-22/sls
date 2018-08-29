package sls.ruben.strexleadsystem.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sls.ruben.strexleadsystem.R
import android.content.Intent
import sls.ruben.strexleadsystem.prefService
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners


class SplashActivity : AppCompatActivity(), OnConnectionTimeoutListeners {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(
                this@SplashActivity,
                if (prefService.userStored) MainViewActivity::class.java else LoginViewActivity::class.java
        ))

        finish()

    }

    override fun onConnectionTimeout() {
        prefService.noServerConnection = true
        startActivity(Intent(
                this@SplashActivity,
                if (prefService.userStored) MainViewActivity::class.java else LoginViewActivity::class.java
        ))

        finish()
    }
}
