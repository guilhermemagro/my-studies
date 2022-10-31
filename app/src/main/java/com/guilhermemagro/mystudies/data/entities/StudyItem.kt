package com.guilhermemagro.mystudies.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_items")
data class StudyItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "parent_id") val parentId: Int? = null,
    val title: String,
    @ColumnInfo(name = "already_read") val alreadyRead: Boolean = false,
    val depth: Int = 0,
)
