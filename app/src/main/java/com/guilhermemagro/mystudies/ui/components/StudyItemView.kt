package com.guilhermemagro.mystudies.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.guilhermemagro.mystudies.data.entities.StudyItem

private const val DEPTH_SPACE = 16

@Composable
fun StudyItemView(
    modifier: Modifier = Modifier,
    studyItem: StudyItem,
    isOnEditMode: Boolean = false,
    onCheckedChange: (StudyItem) -> Unit = {},
    onDeleteItem: (StudyItem) -> Unit = {},
    onAddStudySubItem: (StudyItem) -> Unit = {},
    onBlankItemTitle: () -> Unit = {},
) {
    val isOnAddSubItemState = remember { mutableStateOf(false) }

    Column() {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(studyItem.depth * DEPTH_SPACE.dp))
            if (isOnEditMode) {
                IconButton(onClick = { isOnAddSubItemState.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add subitem"
                    )
                }
            } else {
                Checkbox(
                    checked = studyItem.alreadyRead,
                    onCheckedChange = {
                        onCheckedChange(studyItem.copy(alreadyRead = !studyItem.alreadyRead))
                    }
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = studyItem.title,
                maxLines = 1
            )
            if (isOnEditMode) {
                IconButton(onClick = { onDeleteItem(studyItem) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete item"
                    )
                }
            }
        }
        if (isOnAddSubItemState.value) {
            ConfirmTextField(
                onCancelClickListener = { isOnAddSubItemState.value = false },
                onDoneClickListener = {
                    isOnAddSubItemState.value = false
                    if (it.isNotBlank()) {
                        val newStudySubItem = StudyItem(
                            parentId = studyItem.id,
                            title = it,
                            depth = studyItem.depth.inc()
                        )
                        onAddStudySubItem(newStudySubItem)
                    } else {
                        onBlankItemTitle()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyItemPreview() {
    StudyItemView(
        studyItem = StudyItem(
            title = "Title test",
            depth = 0
        )
    )
}
