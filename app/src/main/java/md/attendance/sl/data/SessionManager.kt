package md.attendance.sl.data

import android.content.Context
import androidx.core.content.edit

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
    }

    fun saveLogin(username: Int) {
        prefs.edit(commit = true) {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putInt(KEY_USERNAME, username)
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }


    fun logout() {
        prefs.edit(commit = true) {
            putBoolean(KEY_IS_LOGGED_IN, false)
            putInt(KEY_USERNAME, -1)
        }
    }
}
