package md.attendance.sl.ui.history.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import md.attendance.sl.data.history.HistoryEntity
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.di.GeocoderHelper
import md.attendance.sl.use_case.HistoryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyUseCase: HistoryUseCase) :
    ViewModel() {

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

    fun updateEntity(updatedEntity: HistoryEntity) {
        viewModelScope.launch {
            historyUseCase.updateHistory(updatedEntity)
        }
    }

    fun deleteEntity(updatedEntity: HistoryEntity) {
        viewModelScope.launch {
            historyUseCase.deleteHistory(updatedEntity)
        }
    }
    // Keep track of the model being edited inside the ViewModel
    var editedModel: HistoryEntity? = null
        private set

    val checkInAddress = MutableStateFlow<String?>(null)
    val checkOutAddress = MutableStateFlow<String?>(null)

    fun initializeModel(context: Context,initialHistory: HistoryEntity) {
        if (editedModel == null) {
            editedModel = initialHistory.copy()
            // Fetch addresses ONLY ONCE when model is first initialized
            fetchAddresses(context,initialHistory)
        }
    }

    fun updateCheckInLocation(lat: Double, lng: Double, context: Context) {
        editedModel = editedModel?.copy(latitude = lat, longitude = lng)
        viewModelScope.launch {
            checkInAddress.value = getAddress(context, lat, lng)
        }
    }

    fun updateCheckOutLocation(lat: Double, lng: Double, context: Context) {
        editedModel = editedModel?.copy(checkOutLatitude = lat, checkOutLongitude = lng)
        viewModelScope.launch {
            checkOutAddress.value = getAddress(context, lat, lng)
        }
    }

    fun updateTimes(checkIn: String, checkOut: String) {
        editedModel = editedModel?.copy(checkInTime = checkIn, checkoutTime = checkOut)
    }

    private fun fetchAddresses(context: Context,history: HistoryEntity) {
        viewModelScope.launch {
            checkInAddress.value = getAddress(context, history.latitude, history.longitude)
            checkOutAddress.value = getAddress(context, history.checkOutLatitude, history.checkOutLongitude)
        }
    }
    suspend fun getAddress(
        context: Context,
        latitude: Double?,
        longitude: Double?
    ): String? {

        if (
            latitude == null ||
            longitude == null
        ) {

            return null
        }

        return GeocoderHelper
            .getAddressFromLatLng(
                context,
                latitude,
                longitude
            )
    }
}
