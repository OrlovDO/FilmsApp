package tech.orlov.data.db.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import tech.orlov.data.db.RoomDateConverter
import java.util.*

@Entity
@TypeConverters(RoomDateConverter::class)
data class CacheTimestampDbo(
    @PrimaryKey
    @ColumnInfo(name = "cache_time")
    val cacheTime: Date
)