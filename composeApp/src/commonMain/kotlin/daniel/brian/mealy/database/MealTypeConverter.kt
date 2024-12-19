package daniel.brian.mealy.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

class MealTypeConverter {
    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string: String?): List<String>? {
        return string?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }
    }

    @TypeConverter
    fun fromBooleanToInt(boolean: Boolean?): Int? {
        return boolean?.let { if (it) 1 else 0 }
    }

    @TypeConverter
    fun fromIntToBoolean(int: Int?): Boolean? {
        return int?.let { it == 1 }
    }
}