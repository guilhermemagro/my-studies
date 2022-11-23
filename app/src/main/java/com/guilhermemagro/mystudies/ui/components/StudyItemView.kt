package com.guilhermemagro.mystudies.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.utils.PATH_DIVIDER

private const val DEPTH_SPACE = 16

@Composable
fun StudyItemView(
    modifier: Modifier = Modifier,
    studyItem: StudyItem,
    isOnEditMode: Boolean = false,
    isExpanded: Boolean = false,
    hasChild: Boolean = false,
    onCheckedChange: (StudyItem) -> Unit = {},
    onDeleteItem: (StudyItem) -> Unit = {},
    onAddStudySubItem: (StudyItem) -> Unit = {},
    onBlankItemTitle: () -> Unit = {},
    onExpand: (StudyItem) -> Unit = {},
) {
    var isOnAddSubItemState by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    if (hasChild) {
                        onExpand(studyItem)
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(studyItem.depth * DEPTH_SPACE.dp))
            if (isOnEditMode) {
                IconButton(onClick = { isOnAddSubItemState = true }) {
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
            if (hasChild) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = if (isExpanded) {
                        Modifier.rotate(180f)
                    } else {
                        Modifier
                    }
                )
            }
            if (isOnEditMode) {
                IconButton(onClick = { onDeleteItem(studyItem) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete item"
                    )
                }
            }
        }
        if (isOnAddSubItemState) {
            ConfirmTextField(
                onCancelClickListener = { isOnAddSubItemState = false },
                onDoneClickListener = {
                    isOnAddSubItemState = false
                    if (it.isNotBlank()) {
                        val newStudySubItem = StudyItem(
                            parentId = studyItem.id,
                            parentPath = studyItem.parentPath + PATH_DIVIDER + studyItem.id,
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
