package com.guilhermemagro.mystudies.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guilhermemagro.mystudies.ui.HomeScreen
import com.guilhermemagro.mystudies.viewmodels.HomeViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val studyItems by homeViewModel.studyItems.observeAsState()
            HomeScreen(
                scaffoldState = scaffoldState,
                studyItems = studyItems,
                updateStudyItem = {
                    homeViewModel.updateStudyItem(it)
                },
                onAddStudyItemDone = {
                    homeViewModel.addStudyItem(it)
                }
            )
        }
    }
}
