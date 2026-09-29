package com.yodgorbek.openmeet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.yodgorbek.openmeet.ui.navigation.OpenMeetNavGraph
import com.yodgorbek.openmeet.ui.theme.OpenMeetTheme
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.User
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    
    private val chatClient: ChatClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Connect a test user
        val user = User(
            id = "test-user-1",
            name = "Test User",
            image = "https://bit.ly/2TIt8NR"
        )
        chatClient.connectUser(user = user, token = chatClient.devToken(user.id)).enqueue { result ->
            if (result.isSuccess) {
                // User connected successfully
            }
        }
        
        setContent {
            OpenMeetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpenMeetNavGraph()
                }
            }
        }
    }
}
