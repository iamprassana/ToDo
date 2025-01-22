package com.example.todo.pages

sealed class Screens(val route : String) {

    object MainPage : Screens("mainPage")
    object AddUpdate : Screens("addPage")
}