package com.guilhermemagro.mystudies.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class StudyItemWithSubStudyItems(
    @Embedded
    val studyItem: StudyItem,

    @Relation(
        parentColumn = "id",
        entityColumn = "parent_id"
    )
    val subStudyItems: List<StudyItem>
)
