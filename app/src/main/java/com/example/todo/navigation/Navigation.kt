package com.example.todo.navigation

import AppViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.pages.AddUpdate2
//import com.example.todo.pages.AddUpdateToDo
import com.example.todo.pages.MainPageView
import com.example.todo.pages.PriorityScreen
import com.example.todo.pages.Screens

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    NavHost(navController, startDestination = Screens.MainPage.route) {

        composable(Screens.MainPage.route) {
            MainPageView(navController, viewModel)
        }

        composable(
            Screens.AddUpdate.route + "/{id}", arguments =
            listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = 0L
                nullable = false
            })
        ) {

            val id =if(it.arguments != null) it.arguments!!.getLong("id") else 0L

            AddUpdate2(navController,id, viewModel)

        }

        composable(route = Screens.PriorityScreen.route + "/{priority}") {

            val priority = it.arguments!!.getString("priority")

            if (priority != null) {
                PriorityScreen(priority = priority , navController, viewModel)
            }
        }
    }

}