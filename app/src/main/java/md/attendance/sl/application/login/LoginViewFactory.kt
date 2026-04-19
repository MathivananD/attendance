package md.attendance.sl.application.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import md.attendance.sl.ui.login.view_model.LoginViewModel
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val useCase: LoginUserCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(useCase) as T
    }
}