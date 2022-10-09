package com.guilhermemagro.mystudies.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
}
