package com.projects.android.mvvmpagingnewsapp.database

import androidx.room.TypeConverter
import com.projects.android.mvvmpagingnewsapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}