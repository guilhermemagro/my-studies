package com.guilhermemagro.mystudies.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    onCheckedChange: (StudyItem) -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = studyItem.alreadyRead,
            onCheckedChange = {
                onCheckedChange(studyItem.copy(alreadyRead = !studyItem.alreadyRead))
            }
        )
        Spacer(modifier = Modifier.width(studyItem.depth * DEPTH_SPACE.dp))
        Text(text = studyItem.title)
    }
}

@Preview(showBackground = true)
@Composable
fun StudyItemPreview() {
    StudyItemView(
        studyItem = StudyItem(
            title = "Title test",
            depth = 1
        )
    )
}
