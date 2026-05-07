package md.attendance.sl.ui.history.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.use_case.HistoryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val historyUseCase: HistoryUseCase) : ViewModel() {

    private val _state: MutableLiveData<UiState<List<HistoryEntity>>> =
        MutableLiveData(UiState.Idle)


    val states: LiveData<UiState<List<HistoryEntity>>> = _state

    fun load() {
        _state.value = UiState.Loading

        viewModelScope.launch {
            historyUseCase.getHistory()
                .catch { e ->
                    Log.d("HistoryViewModel", "Failed to load history: ${e.message}")
                    _state.postValue(
                        UiState.Error(
                            e.message ?: "Something went wrong"
                        )
                    )
                }
                .collectLatest { list ->
                    _state.postValue(UiState.Success(list))
                }
        }
    }

}
