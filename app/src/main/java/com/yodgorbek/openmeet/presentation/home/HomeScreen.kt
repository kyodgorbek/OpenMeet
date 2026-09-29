package com.yodgorbek.openmeet.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    androidx.compose.foundation.layout.Column(
        modifier = Modifier.fillMaxSize(), 
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("chat") }) {
            Text(text = "Open AI Chat")
        }
        
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(16.dp))
        
        Button(onClick = { navController.navigate("call/ai-meeting-room") }) {
            Text(text = "Join Video Call")
        }
    }
}
