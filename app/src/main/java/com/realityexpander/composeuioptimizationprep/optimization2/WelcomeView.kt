package com.realityexpander.composeuioptimizationprep.optimization2

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.plcoding.external_module.ExternalUser
import java.util.*

// Problem - will always recompose
@Composable
fun WelcomeView(
    user: ExternalUser
) {
    Text(text = "Welcome ${user.username}!")
}

// Option 1
// Primitives are marked as @Stable
@Composable
fun WelcomeViewAlternative(
    username: String,
) {
    Text(text = "Welcome $username!")
}

// Option 2
// Use a mapped class from this module
@Composable
fun WelcomeViewAlternative2(
    user: User
) {
    Text(text = "Welcome ${user.username}!")
}

// Option 3
// Use a @Stable object
@Composable
fun WelcomeViewAlternative3(
    user: ExternalUser
) {
    val username by remember(user) {
        mutableStateOf(user.username)
    }
    Text(text = "Welcome $username!")
}

@Composable
fun Optimization2(
    user2: ExternalUser
) {
    var user by remember {
        mutableStateOf(
            ExternalUser(
                id = "1",
                email = "a@demo.com",
                username = "John"
            )
        )
    }

    Column() {

        // Problem - recomposes every time (even when user2 is the same)
//        WelcomeView(user)
//        WelcomeView(user2)

        // Option 1 - recomposes only when username field changes
//        WelcomeViewAlternative(username = user.username)
//        WelcomeViewAlternative(username = user2.username)

        // Option 2 - recomposes only when MAPPED user changes
//        WelcomeViewAlternative2(user.toUser())
//        WelcomeViewAlternative2(user2.toUser())

        // Option 3 - Uses remember to store username in a local @Stable object
        WelcomeViewAlternative3(user)
        WelcomeViewAlternative3(user2) // note the component will recompose but the text will not

        Button(onClick = {
            user = ExternalUser(
                id = UUID.randomUUID().toString(),
                email = "b@demo.com",
                username = "Changed" + kotlin.random.Random.nextInt(0, 100)
            )
        }) {
            Text(text = "Change user")
        }

    }
}