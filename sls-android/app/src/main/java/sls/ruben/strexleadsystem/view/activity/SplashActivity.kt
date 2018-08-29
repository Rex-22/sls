package sls.ruben.strexleadsystem.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sls.ruben.strexleadsystem.R
import android.content.Intent
import io.reactivex.schedulers.Schedulers
import sls.ruben.strexleadsystem.api.ApiService
import sls.ruben.strexleadsystem.prefService
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners


class SplashActivity : AppCompatActivity(), OnConnectionTimeoutListeners {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ApiService.add(this)
        try {
            ApiService.common.ping()
                    .subscribeOn(Schedulers.newThread())
                    .subscribe({
                        runOnUiThread {
                            startActivity(Intent(
                                    this@SplashActivity,
                                    if (prefService.userStored) MainViewActivity::class.java else LoginViewActivity::class.java
                            ))

                            finish()
                        }
                        prefService.noServerConnection = false
                    }, {
                        prefService.noServerConnection = true
                        runOnUiThread {
                            startActivity(Intent(
                                    this@SplashActivity,
                                    if (prefService.userStored) MainViewActivity::class.java else LoginViewActivity::class.java
                            ))

                            finish()
                        }
                    })
        } catch (e: Exception) {
            prefService.noServerConnection = true
            startActivity(Intent(
                    this@SplashActivity,
                    if (prefService.userStored) MainViewActivity::class.java else LoginViewActivity::class.java
            ))

            finish()
        }

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
