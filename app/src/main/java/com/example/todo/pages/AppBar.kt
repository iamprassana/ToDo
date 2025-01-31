package com.example.todo.pages

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navHostController: NavHostController, title: String) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val navigationIcon: (@Composable () -> Unit) = {
        if (!title.lowercase().contains("todo")) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Arrow",
                tint = colorResource(R.color.page),
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 5.dp)
                    .clickable { navHostController.navigateUp() })
        }
    }

    val actionIcon: (@Composable () -> Unit) = {
        if (title.lowercase().contains("todo")) {
            IconButton(onClick = { isDropdownExpanded = true }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_filter_alt_24),
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }

    TopAppBar(
        title = {
            Text(
                title,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                ),
                modifier = Modifier.padding(15.dp),
                color = colorResource(R.color.page),
                textAlign = TextAlign.Start
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(colorResource(R.color.appBar)),
        navigationIcon = navigationIcon,
        actions = { actionIcon() },
        modifier = Modifier.fillMaxWidth()
    )

    // Dropdown Menu Below the AppBar
    DropDown(isDropdownExpanded, navHostController) {
        isDropdownExpanded = false
    }
}

@Composable
fun DropDown(expanded: Boolean, navHostController: NavHostController, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter)
            .clip(
                RoundedCornerShape(12.dp)
            ),
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth(0.455f)
                .clip(RoundedCornerShape(0.dp))
                .background(colorResource(R.color.page)),
            offset = DpOffset(x = 1200.dp, y = 90.dp),
            properties = PopupProperties(
                focusable = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            DropdownMenuItem(
                onClick = {
                    navHostController.navigate("${Screens.PriorityScreen.route}/high")
                    onDismiss()
                }, text = {
                    Text("High")
                }, colors = MenuDefaults.itemColors(colorResource(R.color.appBar))
            )
            DropdownMenuItem(
                onClick = {
                    navHostController.navigate("${Screens.PriorityScreen.route}/mid")

                    onDismiss()
                }, text = {
                    Text("Medium")
                },
                colors = MenuDefaults.itemColors(colorResource(R.color.appBar))
            )
            DropdownMenuItem(
                onClick = {
                    navHostController.navigate("${Screens.PriorityScreen.route}/low")

                    onDismiss()
                }, text = {
                    Text("Low")
                },
                colors = MenuDefaults.itemColors(colorResource(R.color.appBar))
            )
        }
    }
}
