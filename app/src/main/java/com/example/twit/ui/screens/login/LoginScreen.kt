package com.example.twit.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.twit.R
import com.example.twit.navigation.CalendarScreen
import com.example.twit.navigation.MainScreen
import com.example.twit.ui.theme.TwitTheme
import com.example.twit.ui.theme.animeTypography


@Composable
fun Login(model: LoginViewModel = viewModel(), navController: NavController) {
    TwitTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Content(model, navController)
        }
    }
}

@Composable
fun Content(model: LoginViewModel, navController: NavController) {
    val loginUiState by model.uiState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.app_name),
            fontFamily = animeTypography.bodyLarge.fontFamily,
            fontSize = 60.sp,
            fontWeight = animeTypography.bodyLarge.fontWeight,)

        TextFieldEmail(
            value = loginUiState.email,
            text = "email",
            onChange = {
                model.emailText(it)
                model.checkLogin(it, loginUiState.password)
            })

        TextFieldPassword(
            value = loginUiState.password,
            text = "password",
            visibility = loginUiState.showPassword,
            onChange = {
                model.passwordText(it)
                model.checkLogin(loginUiState.email, it)
            },
            changeVisibility = { model.changeVisibility(it) }

        )

        Text(
            text = "Forgot password?",
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        )

        Button(
            enabled = loginUiState.LogInEnable,
            onClick = { navController.navigate(CalendarScreen) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Log in")
        }
        DividerCustom()
    }
}

@Composable
fun TextFieldEmail(value: String, text: String, onChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onChange,
        singleLine = true,
        label = {
            Text(
                text = text
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun TextFieldPassword(
    value: String,
    text: String,
    visibility: Boolean,
    onChange: (String) -> Unit,
    changeVisibility: (Boolean) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onChange,
        singleLine = true,
        label = {
            Text(
                text = text
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        trailingIcon = {
            val icon = if (visibility) {
                R.drawable.baseline_visibility_24
            } else {
                R.drawable.baseline_visibility_off_24
            }
            IconButton(onClick = { changeVisibility(visibility) }) {
                Icon(painter = painterResource(id = icon), contentDescription = null)
            }
        },
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun DividerCustom() {
    Row {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(text = "OR", modifier = Modifier.padding(horizontal = 16.dp))
        HorizontalDivider(modifier = Modifier.weight(1f))
    }

}
