package md.attendance.sl.data.history

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var checkInTime: String,
    var checkoutTime: String,
    val userId: Int = -1,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val checkOutLatitude: Double? = 0.0,
    val checkOutLongitude: Double? = 0.0
) : Parcelable