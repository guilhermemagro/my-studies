package com.guilhermemagro.mystudies.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guilhermemagro.mystudies.utils.PATH_DIVIDER
import com.guilhermemagro.mystudies.utils.ROOT_PARENT_ID

@Entity(tableName = "study_items")
data class StudyItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "parent_id") val parentId: Int = 0,
    @ColumnInfo(name = "parent_path") val parentPath: String = "$ROOT_PARENT_ID$PATH_DIVIDER",
    val title: String,
    @ColumnInfo(name = "already_read") val alreadyRead: Boolean = false,
    val depth: Int = 0,
) {
    fun getPath() = parentPath + id + PATH_DIVIDER

    fun isChildOf(possibleParent: StudyItem): Boolean {
        return parentPath.startsWith(possibleParent.getPath())
    }
}
