package com.thatsmanmeet.calculator.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true) val id:Int?,
    @ColumnInfo(name = "result_data") val result: String
)
