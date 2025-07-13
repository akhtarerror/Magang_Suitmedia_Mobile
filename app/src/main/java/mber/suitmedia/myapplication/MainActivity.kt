package mber.suitmedia.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mber.suitmedia.myapplication.ui.screens.FirstScreen
import mber.suitmedia.myapplication.ui.screens.SecondScreen
import mber.suitmedia.myapplication.ui.screens.ThirdScreen
import mber.suitmedia.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                var userName by remember { mutableStateOf("") }
                var selectedUserName by remember { mutableStateOf("Selected User Name") }

                NavHost(
                    navController = navController,
                    startDestination = "first_screen"
                ) {
                    composable("first_screen") {
                        FirstScreen(
                            navController = navController,
                            onNameChange = { userName = it }
                        )
                    }
                    composable("second_screen") {
                        SecondScreen(
                            navController = navController,
                            userName = userName,
                            selectedUserName = selectedUserName
                        )
                    }
                    composable("third_screen") {
                        ThirdScreen(
                            navController = navController,
                            onUserSelected = { selectedUserName = it }
                        )
                    }
                }
            }
        }
    }
}