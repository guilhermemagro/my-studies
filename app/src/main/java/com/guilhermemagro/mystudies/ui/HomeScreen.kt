package com.guilhermemagro.mystudies.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.ui.components.ConfirmTextField

@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    studyItems: List<StudyItem>? = null,
    onAddStudyItemDone: (String) -> Unit = {}
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        HomeScreenContent(
            scaffoldState = scaffoldState,
            studyItems = studyItems,
            onAddStudyItemDone = onAddStudyItemDone
        )
    }
}

@Composable
fun HomeScreenContent(
    scaffoldState: ScaffoldState,
    studyItems: List<StudyItem>? = null,
    onAddStudyItemDone: (String) -> Unit = {}
) {
    val openCreateStudyItemDialog = remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    // DIALOG <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    if (openCreateStudyItemDialog.value) {
        AlertDialog(
            onDismissRequest = { openCreateStudyItemDialog.value = false },
            title = {
                Text(text = "Digite o nome do item a ser criado:")
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp)
                ) {
                    Button(
                        onClick = { openCreateStudyItemDialog.value = false },
                        modifier = Modifier.weight(1f, true)
                    ) {
                        Text(text = "Cancelar")
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        onClick = {
                            onAddStudyItemDone("TODO")
                            openCreateStudyItemDialog.value = false
                        },
                        modifier = Modifier.weight(1f, true),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFEC532F),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Adicionar",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                        Text(text = "Adicionar")
                    }
                }
            }
        )
    }

    // SCREEN <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            studyItems?.takeIf { it.isNotEmpty() }?.let { studyItems ->
                items(studyItems) { studyItem ->
                    Text(text = studyItem.title)
                }
            } ?: run {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Icon(Icons.Filled.Warning, null)
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            text = "Você não tem nenhum item de estudo cadastrado!",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Button(
                    onClick = { openCreateStudyItemDialog.value = true },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Adicionar item",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = "Adicionar item")
                }
            }
            item {
                ConfirmTextField(
                    text = "", onValueChange = {},
                    onCancelClickListener = {
                        Log.e("ConfirmTextField", "onCancelClickListener")
                    },
                    onDoneClickListener = {
                        Log.e("ConfirmTextField", "onDoneClickListener")
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(
        scaffoldState = rememberScaffoldState(),
    )
}
