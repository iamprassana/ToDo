package com.example.todo.pages

import AppViewModel
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todo.R
import com.example.todo.data.ToDo

@Composable
fun AddUpdateToDo(
    navController: NavHostController,
    id: Long,
    viewModel: AppViewModel,
) {


    if (id != 0L ) {
        val todo = viewModel.getToDoById(id).collectAsState(initial = ToDo(0L, "", false))
        println("${todo.value.id} , ${todo.value.title}, ${todo.value.isChecked}")
        viewModel.title = todo.value.title
        viewModel.isChecked = todo.value.isChecked
    } else {
        viewModel.title = ""
        viewModel.isChecked = false
    }

    val context = LocalContext.current
    val title = if (id == 0L) "Add" else "Update"
    AlertDialog(
        title = { Text(title) },
        text = {

            OutlinedTextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onTitleChange(it)
                },
                label = {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            )

        },
        containerColor = colorResource(R.color.appBar),
        onDismissRequest = {
            navController.navigateUp()
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(colorResource(R.color.buttonColor)),
                onClick = {
                    navController.navigateUp()
                },
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(0.dp)
            ) {
                Text("Dismiss")
            }

        },
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(colorResource(R.color.buttonColor)),
                onClick = {
                    if (viewModel.title.isNotEmpty()
                    ) {
                        if (id != 0L) {
                            viewModel.updateToDo(
                                ToDo(
                                    id = id,
                                    title = viewModel.title,
                                    isChecked = viewModel.isChecked
                                )
                            )
                            navController.navigateUp()
                        } else {
                            //  AddWish
                            viewModel.addToDo(
                                ToDo(
                                    title = viewModel.title,
                                    isChecked = viewModel.isChecked
                                )
                            )
                            navController.navigateUp()
                        }
                    } else {
                        Toast.makeText(context, "Field Cannot Be Empty", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(0.dp)
            ) {
                if (id == 0L) {
                    Text(
                        "Add ", style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                } else {
                    Text(
                        "Update", style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                }
            }
        }
    )
}