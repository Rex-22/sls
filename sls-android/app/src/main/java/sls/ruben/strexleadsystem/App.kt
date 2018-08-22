package sls.ruben.strexleadsystem

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.room.Room
import androidx.room.RoomDatabase
import sls.ruben.strexleadsystem.api.ApiService
import sls.ruben.strexleadsystem.database.AppDatabase
import tech.bitcube.sabu.service.PrefService
import timber.log.Timber
import java.util.*

/*
* This is where any global initialization should occur
* */

val prefService: PrefService by lazy {
    App.prefs!!
}

val database: RoomDatabase by lazy {
    App.database!!
}

val api: ApiService by lazy {
    App.api!!
}

class App : Application() {

    companion object {
        var prefs: PrefService? = null
        var database: AppDatabase? = null
        var api: ApiService? = null

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            // Clear focus to avoid showing the keyboard again if it is opened from the background
            view.clearFocus()
        }

        fun log(e: Throwable?) {
            e!!.printStackTrace()
            Timber.e(e)
        }

    }

    // Do all initialization in this function as it is called before any activity is instantiated
    override fun onCreate() {
        super.onCreate()
        prefs = PrefService(applicationContext)

        api = ApiService

        database = Room.databaseBuilder(this, AppDatabase::class.java, "sabu")
                .fallbackToDestructiveMigration()
                .build()
    }
}