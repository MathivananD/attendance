package md.attendance.sl.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import md.attendance.sl.data.enums.CheckInOutEnum
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.ui_state.HomeState
import md.attendance.sl.use_case.HomeUserCase
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.di.DateTimeHelper
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userCase: HomeUserCase,
    val dateTimeHelper: DateTimeHelper,
) : ViewModel() {
    private val _state = MutableLiveData<HomeState>(HomeState.Idle)

    val states: LiveData<HomeState> = _state

    private val _runningTime =
        MutableStateFlow("00:00:00")

    val runningTime: StateFlow<String> = _runningTime
    private val _user = MutableStateFlow<UserEntity?>(null)

    val user: MutableStateFlow<UserEntity?> = _user
    private val _todayHistoryEntity: MutableLiveData<HistoryEntity?> = MutableLiveData(null)

    val todayHistoryEntity: LiveData<HistoryEntity?> = _todayHistoryEntity
    private var timerJob: Job? = null
    private var isUserLoaded = false
    private val _isCheckedIn: MutableLiveData<CheckInOutEnum> = MutableLiveData(CheckInOutEnum.NONE)
    val isCheckedIn: LiveData<CheckInOutEnum> = _isCheckedIn

    val isCheckedInBoolean =
        isCheckedIn.map {

            it == CheckInOutEnum.CHECKEDIN
        }
    val isCheckOutBoolean =
        isCheckedIn.map {

            it == CheckInOutEnum.CHECKOUT
        }

    init {
        loadUser()
    }

    fun loadUser() {
        if (isUserLoaded || _state.value == HomeState.Loading) return

        _state.value = HomeState.Loading

        viewModelScope.launch {

            delay(4000)

            _user.value = userCase.getUser()

            val currentEntity: HistoryEntity? = getCurrentCheckIn()
            _todayHistoryEntity.value = currentEntity

            if (currentEntity == null) {
                _isCheckedIn.value =
                    CheckInOutEnum.NONE
            } else if (currentEntity.checkoutTime.isEmpty()) {
                _isCheckedIn.value =
                    CheckInOutEnum.CHECKEDIN
                val checkInMilliSeconds =
                    dateTimeHelper.dateToMillis(todayHistoryEntity.value!!.checkInTime)

                startTimer(checkInMilliSeconds!!)
            } else {
                val checkInMilliSeconds =
                    dateTimeHelper.dateToMillis(todayHistoryEntity.value!!.checkInTime)
                val checkOutMilliSeconds =
                    dateTimeHelper.dateToMillis(todayHistoryEntity.value!!.checkoutTime)
                if (checkInMilliSeconds != null && checkOutMilliSeconds != null) {

                    _runningTime.value =
                        dateTimeHelper.getWorkedTime(checkInMilliSeconds, checkOutMilliSeconds)

                }
                _isCheckedIn.value =
                    CheckInOutEnum.CHECKOUT
            }




            _state.value =
                HomeState.Success("Loaded")
            isUserLoaded = true
        }
    }

    fun startTimer(
        checkInMillis: Long
    ) {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            while (isActive) {
                _runningTime.value =
                    dateTimeHelper.getWorkedTime(
                        checkInTimeMillis = checkInMillis
                    )
                delay(1000)
            }
        }
    }

    fun stopTimer() {

        timerJob?.cancel()
    }

    fun getCurrentDateTime(): String {

        return dateTimeHelper.getCurrentDateTime()
    }

    fun getCheckInTime(): String {
        if (todayHistoryEntity.value == null) return ""
        return dateTimeHelper.getFormatTime(todayHistoryEntity.value!!.checkInTime)
    }

    fun getCheckOutTime(): String {
        if (todayHistoryEntity.value == null) return ""
        if (todayHistoryEntity.value
            !!.checkoutTime.isEmpty()
        ) return ""
        return dateTimeHelper.getFormatTime(todayHistoryEntity.value!!.checkoutTime)
    }


    suspend fun getCurrentCheckIn(): HistoryEntity? {
        val currentDate = dateTimeHelper.getCurrentDate()
        return userCase.getTodayCheckIn(currentDate)
    }

    fun callCheckInCheckOut() {
        viewModelScope.launch {
            val history = getCurrentCheckIn()

            if (history == null) {
                callCheckIn()
            } else {
                callCheckOut(history)
            }
        }
    }

    private suspend fun callCheckIn() {
        val currentTime = dateTimeHelper.getCurrentDateTime()
        val history = HistoryEntity(
            id = 0,
            checkInTime = currentTime,
            checkoutTime = "",
            userId = userCase.getCurrentUserId()
        )
        val id = userCase.callCheckIn(history)
        _todayHistoryEntity.value = HistoryEntity(
            id = id.toInt(),
            checkInTime = history.checkInTime,
            checkoutTime = history.checkoutTime,
            userId = history.userId
        )
        val dateInMilliSeconds =
            dateTimeHelper.dateToMillis(todayHistoryEntity.value!!.checkInTime!!)
        if (dateInMilliSeconds != null) {
            startTimer(dateInMilliSeconds)

        }
        _isCheckedIn.value = CheckInOutEnum.CHECKEDIN
    }

    private suspend fun callCheckOut(history: HistoryEntity) {
        val currentTime = dateTimeHelper.getCurrentDateTime()
        history.checkoutTime = currentTime
        userCase.callCheckOut(history)
        _todayHistoryEntity.value = history
        stopTimer()
        _isCheckedIn.value = CheckInOutEnum.CHECKOUT
    }
}
