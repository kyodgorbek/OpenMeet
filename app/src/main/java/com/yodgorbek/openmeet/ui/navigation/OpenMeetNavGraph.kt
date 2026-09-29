package com.yodgorbek.openmeet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yodgorbek.openmeet.presentation.home.HomeScreen
import com.yodgorbek.openmeet.presentation.chat.ChatScreen
import com.yodgorbek.openmeet.presentation.call.CallScreen

import com.yodgorbek.openmeet.presentation.login.LoginScreen

@Composable
fun OpenMeetNavGraph() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { 
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("chat") {
            ChatScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable("call/{callId}") { backStackEntry ->
            val callId = backStackEntry.arguments?.getString("callId") ?: "default-call"
            CallScreen(
                callId = callId,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}
