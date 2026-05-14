package md.attendance.sl.data.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HistoryEntity::class], version = 2, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        val MIGRATION_1_2 =
            object : Migration(1, 2) {

                override fun migrate(
                    db: SupportSQLiteDatabase,
                ) {

                    db.execSQL(
                        """
                ALTER TABLE history_table
                ADD COLUMN lat TEXT
                """
                    )

                    db.execSQL(
                        """
                ALTER TABLE history_table
                ADD COLUMN long TEXT
                """
                    )
                }
            }

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}