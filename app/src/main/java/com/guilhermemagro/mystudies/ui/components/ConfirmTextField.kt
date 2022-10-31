package com.guilhermemagro.mystudies.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ConfirmTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit = {},
    onCancelClickListener: () -> Unit = {},
    onDoneClickListener: (String) -> Unit = {}
) {
    var textState by rememberSaveable { mutableStateOf(text) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textState,
        onValueChange = {
            textState = it
            onValueChange(it)
        },
        trailingIcon = {
            Row() {
                IconButton(onClick = onCancelClickListener) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Cancel",
                        tint = Color.Red
                    )
                }
                IconButton(onClick = { onDoneClickListener(textState) } ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Done",
                        tint = Color.Green
                    )
                }
            }
        }
    )
}
