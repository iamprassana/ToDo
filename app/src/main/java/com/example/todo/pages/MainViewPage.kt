package com.example.todo.pages

import AppViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todo.R
import com.example.todo.data.ToDo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainPageView(navHostController: NavHostController, viewModel: AppViewModel) {


    Scaffold(
        topBar = { AppBar("ToDo") },
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.page),
        floatingActionButton = {
            AnimatedVisibility(
                visible = true,
                enter = scaleIn(
                    animationSpec = tween(100)
                ) + fadeIn(
                    animationSpec = tween(200)
                ),
                exit = scaleOut(
                    animationSpec = tween(100)
                ) + fadeOut(
                    animationSpec = tween(200)
                )
            ) {
                FloatingActionButton(
                    onClick = { navHostController.navigate("${Screens.AddUpdate.route}/0") },
                    containerColor = colorResource(R.color.button),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        }
    ) {
        ItemViewer(it, viewModel, navHostController)

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
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
            .padding(paddingValues),
    ) {

        items(todoList.value, key = { todo ->
            todo.id
        }) { todo ->

            val state = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(300)
                            viewModel.deleteToDo(todo)
                        }
                        true
                    } else {
                        false
                    }
                }
            )
            val visible by remember { mutableStateOf(true) }
            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = {
                        it
                    },
                    animationSpec = tween(500)
                ) + fadeIn(
                    animationSpec = tween(600)
                ),
                exit = slideOutVertically(
                    targetOffsetY = {it},
                    animationSpec = tween(500)
                ) + fadeOut(
                    animationSpec = tween(600)
                ),


                ) {
                SwipeToDismissBox(
                    state = state,
                    backgroundContent = {},
                    enableDismissFromEndToStart = true,
                    enableDismissFromStartToEnd = false,
                    content = {
                        ItemDesign(todo, viewModel) {
                            val id = todo.id
                            navController.navigate("${Screens.AddUpdate.route}/${id}")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun ItemDesign(todo: ToDo, viewModel: AppViewModel, onClick: () -> Unit) {


    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.card)),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable { onClick() },

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


            if (todo.isChecked) {
                Text(
                    todo.title,
                    style = TextStyle(fontSize = 22.sp, fontFamily = FontFamily.Monospace),
                    color = colorResource(R.color.appBar),
                    textDecoration = TextDecoration.LineThrough
                )
            } else {
                Text(
                    todo.title,
                    style = TextStyle(fontSize = 22.sp, fontFamily = FontFamily.Monospace),
                    color = colorResource(R.color.appBar),
                    textDecoration = TextDecoration.None
                )
            }

        }
    }
}
