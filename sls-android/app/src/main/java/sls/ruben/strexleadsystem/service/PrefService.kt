package tech.bitcube.sabu.service

import android.content.Context
import android.content.SharedPreferences

class PrefService(context: Context) {

    private val PREFERENCES_FILENAME = PrefService::class.qualifiedName
    private val AUTH_KEY = "token"
    private val ID = "id"
    private val USER_STORED = "user_stored"
    private val NO_SERVER_CONNECTION = "no_server_connection"

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILENAME, 0)

    var authKey: String?
        get() = sharedPreferences.getString(AUTH_KEY, "")
        set(value) = sharedPreferences.edit().putString(AUTH_KEY, value).apply()

    var id: String?
        get() = sharedPreferences.getString(ID, "")
        set(value) = sharedPreferences.edit().putString(ID, value).apply()

    var userStored: Boolean
        get() = sharedPreferences.getBoolean(USER_STORED, false)
        set(value) = sharedPreferences.edit().putBoolean(USER_STORED, value).apply()

    var noServerConnection: Boolean
        get() = sharedPreferences.getBoolean(NO_SERVER_CONNECTION, false)
        set(value) = sharedPreferences.edit().putBoolean(NO_SERVER_CONNECTION, value).apply()
}