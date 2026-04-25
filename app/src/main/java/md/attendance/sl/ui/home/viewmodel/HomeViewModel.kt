package md.attendance.sl.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import md.attendance.sl.application.home.HomeUserCase
import md.attendance.sl.data.UserEntity
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val userCase: HomeUserCase) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: MutableStateFlow<UserEntity?> = _user

    fun loadUser() {
        val id = userCase.getCurrentUserId()
        viewModelScope.launch {

            _user.value = userCase.getUser(id)
        }
    }
    fun getCurrentDateTime(): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
        return java.time.LocalDateTime.now().format(formatter)
    }
}