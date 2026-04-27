package md.attendance.sl.data.ui_state

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(val message: String) : HomeState()
    data class Error(val error: String) : HomeState()
}