package md.attendance.sl.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.transition.Visibility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.ui_state.HomeState
import md.attendance.sl.data.ui_state.SignupState
import md.attendance.sl.use_case.HomeUserCase
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.di.DateTimeHelper
import java.text.Format
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userCase: HomeUserCase,
    val dateTimeHelper: DateTimeHelper
) : ViewModel() {
    private val _state = MutableLiveData<HomeState>(HomeState.Idle)

    val states: LiveData<HomeState> = _state
    private val _user = MutableStateFlow<UserEntity?>(null)

    val user: MutableStateFlow<UserEntity?> = _user
    private val _isCheckedIn = MutableStateFlow(false)

    val isCheckedIn: MutableStateFlow<Boolean> = _isCheckedIn
    private var _currentUserId: Int? = null

    init {
        loadUser()
    }


    fun loadUser() {
        _state.value = HomeState.Loading
        _currentUserId = userCase.getCurrentUserId()
        viewModelScope.launch {

            _user.value = userCase.getUser(_currentUserId!!)
            _state.value= HomeState.Success("Loaded")
        }
    }

    fun getCurrentDateTime(): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
        return java.time.LocalDateTime.now().format(formatter)
    }

    fun callCheckInCheckOut() {
        val currentDate = dateTimeHelper.getCurrentDate()
        viewModelScope.launch {

            userCase.getTodayCheckIn(
                currentDate,
                _currentUserId!!
            )
                .catch {

                }
                .collect { history ->

                    if (history == null) {

                        // no check-in found

                    } else {

                        // history available

                    }

                }
        }
    }
}