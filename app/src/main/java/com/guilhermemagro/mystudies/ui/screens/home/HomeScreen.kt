package com.guilhermemagro.mystudies.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guilhermemagro.mystudies.data.entities.StudyItem
import com.guilhermemagro.mystudies.extensions.addOrRemoveIfExist
import com.guilhermemagro.mystudies.ui.components.ConfirmTextField
import com.guilhermemagro.mystudies.ui.components.StudyItemView
import com.guilhermemagro.mystudies.utils.ROOT_PARENT_PATH
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val studyItems by homeViewModel.studyItems.observeAsState()
    val isOnEditScreenState = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MyStudies")
                },
                actions = {
                    if (isOnEditScreenState.value) {
                        IconButton(onClick = { isOnEditScreenState.value = false }) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Confirm edit"
                            )
                        }
                    } else {
                        IconButton(onClick = { isOnEditScreenState.value = true }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit items"
                            )
                        }
                    }
                }
            )
        }
    ) {
        HomeScreenContent(
            scaffoldState = scaffoldState,
            studyItems = studyItems,
            updateStudyItem = homeViewModel::updateStudyItem,
            onAddStudyItem = homeViewModel::addStudyItem,
            deleteStudyItem = homeViewModel::deleteStudyItem,
            isOnEditScreenState = isOnEditScreenState
        )
    }
}

@Composable
fun HomeScreenContent(
    scaffoldState: ScaffoldState,
    studyItems: List<StudyItem>? = null,
    updateStudyItem: (StudyItem) -> Unit = {},
    onAddStudyItem: (StudyItem) -> Unit = {},
    deleteStudyItem: (StudyItem) -> Unit = {},
    isOnEditScreenState: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    var showCreateStudyItemTextField by remember { mutableStateOf(false) }
    val parentsExpanded = remember { mutableStateListOf(ROOT_PARENT_PATH) }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    fun onBlankItemTitle() {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "O item não pode ter título vazio."
            )
        }
    }

    // SCREEN <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            studyItems?.takeIf { it.isNotEmpty() }?.let { studyItems ->
                val filteredItems = studyItems.filter {
                    parentsExpanded.contains(it.parentPath)
                }
                items(filteredItems) { studyItem ->
                    StudyItemView(
                        studyItem = studyItem,
                        isOnEditMode = isOnEditScreenState.value,
                        isExpanded = parentsExpanded.contains(studyItem.getPath()),
                        hasChild = studyItems.any { it.parentId == studyItem.id },
                        onCheckedChange = updateStudyItem,
                        onDeleteItem = deleteStudyItem,
                        onAddStudySubItem = onAddStudyItem,
                        onBlankItemTitle = ::onBlankItemTitle,
                        onExpand = {
                            parentsExpanded.addOrRemoveIfExist(studyItem.getPath())
                        }
                    )
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

            // ADD ITEM REGION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            if (isOnEditScreenState.value) {
                if (showCreateStudyItemTextField) {
                    item {
                        ConfirmTextField(
                            onCancelClickListener = {
                                showCreateStudyItemTextField = false
                            },
                            onDoneClickListener = {
                                showCreateStudyItemTextField = false
                                if (it.isNotBlank()) {
                                    onAddStudyItem(StudyItem(title = it))
                                } else {
                                    onBlankItemTitle()
                                }
                            },
                        )
                    }
                } else {
                    item {
                        Button(
                            onClick = { showCreateStudyItemTextField = true },
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
                }
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
