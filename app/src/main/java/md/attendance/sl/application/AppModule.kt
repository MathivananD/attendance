package md.attendance.sl.application

import android.content.Context
import dagger.BindsInstance
import md.attendance.sl.application.login.LoginViewModelFactory

interface AppComponent {

    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }


    fun loginViewModelFactory(): LoginViewModelFactory


}