package md.attendance.sl.data.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data  class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var checkInTime: String,
    var checkoutTime: String,
    val userId: Int=-1
)