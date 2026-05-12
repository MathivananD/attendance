package md.attendance.sl.data.history

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "history_table")
data  class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var checkInTime: String,
    var checkoutTime: String,
    val userId: Int=-1
): Parcelable