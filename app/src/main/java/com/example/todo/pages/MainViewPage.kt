package com.example.todo.pages

import AppViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todo.R
import com.example.todo.data.ToDo

@Composable
fun MainPageView(navHostController: NavHostController, viewModel: AppViewModel) {


    Scaffold(
        topBar = { AppBar("ToDo") },
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.page),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate("${Screens.AddUpdate.route}/0")
                },
                elevation = FloatingActionButtonDefaults.elevation(10.dp),
                containerColor = colorResource(R.color.button),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .size(60.dp)
                    .padding()
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add a todo",
                    tint = colorResource(R.color.page),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    ) {
        ItemViewer(it, viewModel, navHostController)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemViewer(
    paddingValues: PaddingValues,
    viewModel: AppViewModel,
    navController: NavHostController,
) {
    val todoList = viewModel.todoes.collectAsState(initial = listOf())
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .clickable {
                navController.navigate("${Screens.AddUpdate.route}/1")
            }
    ) {

        items(todoList.value, key = { todo ->
            todo.id
        }) { todo ->

            val state = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        viewModel.deleteToDo(todo)
                        true
                    } else {
                        false
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
            SwipeToDismissBox(
                state = state,
                backgroundContent = {},
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false
            ) {
                ItemDesign(todo, viewModel)

            }
        }
    }
}

@Composable
fun ItemDesign(todo: ToDo, viewModel: AppViewModel) {


    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.card)),
        modifier = Modifier.fillMaxWidth(0.9f),

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(19.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = todo.isChecked,
                onCheckedChange = { isChecked ->
                    viewModel.onCheckedChange(isChecked)
                    viewModel.UpdateChecked(todo.id, isChecked)

                },
                colors = CheckboxDefaults.colors(
                    colorResource(R.color.appBar),
                    checkmarkColor = colorResource(R.color.white),
                    uncheckedColor = colorResource(R.color.page)
                ),

                )

            Text(
                todo.title,
                style = TextStyle(fontSize = 22.sp, fontFamily = FontFamily.Monospace),
                color = colorResource(R.color.appBar)
            )
        }
    }
}
