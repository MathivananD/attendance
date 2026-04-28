package md.attendance.sl.ui.history.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import md.attendance.sl.use_case.HistoryUseCase
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(val historyUseCase: HistoryUseCase) : ViewModel() {


}