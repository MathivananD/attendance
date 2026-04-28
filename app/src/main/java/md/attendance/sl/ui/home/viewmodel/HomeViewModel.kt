package md.attendance.sl.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.transition.Visibility
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
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
    val dateTimeHelper: DateTimeHelper,
) : ViewModel() {
    private val _state = MutableLiveData<HomeState>(HomeState.Idle)

    val states: LiveData<HomeState> = _state
    private val _user = MutableStateFlow<UserEntity?>(null)

    val user: MutableStateFlow<UserEntity?> = _user
    private val _isCheckedIn = MutableLiveData(false)
    val isCheckedIn: LiveData<Boolean> = _isCheckedIn


    init {
        loadUser()
    }


    fun loadUser() {
        _state.value = HomeState.Loading
        viewModelScope.launch {

            _user.value = userCase.getUser()
            _isCheckedIn.value = getCurrentCheckIn() != null
            _state.value = HomeState.Success("Loaded")
        }
    }


    fun getCurrentDateTime(): String {

        return dateTimeHelper.getCurrentDateTime()
    }

    fun getCurrentCheckIn(): HistoryEntity? {
        var history: HistoryEntity? = null;
        val currentDate = dateTimeHelper.getCurrentDate()
        viewModelScope.launch {

            userCase.getTodayCheckIn(currentDate)
                .catch {
                    Log.e("CallCheckInCheckOut Error", "${it.message}")

                }
                .collect { it ->
                    history = it

                }

        }
        return history
    }

    fun callCheckInCheckOut() {

        val history = getCurrentCheckIn()
        if (history == null) {
            callCheckIn()
        } else {
            callCheckOut()

        }
    }

    private fun callCheckIn() {
        val currentTime = dateTimeHelper.getCurrentDateTime()
        val history = HistoryEntity(id = 0, checkInTime = currentTime, checkoutTime = "")
        viewModelScope.launch {
            userCase.callCheckIn(history).catch { }.collect { }
        }
    }

    private fun callCheckOut() {
        val currentTime = dateTimeHelper.getCurrentDateTime()
        viewModelScope.launch {
            userCase.getTodayCheckIn(currentTime).catch { }.collect {
                if (it != null) {
                    it.checkoutTime = currentTime
                    userCase.callCheckOut(it)
                }
            }
        }
    }
}