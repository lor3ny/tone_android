package com.lor3n.wahue

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.lor3n.wahue.ui.theme.ToneTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlin.random.Random

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToneTheme {
                // A surface container using the 'background' color from the theme
                auth = Firebase.auth
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF2F2F2)
                ) {
                    LoginPage()
                }
            }
        }
    }

    /*
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToHomepage()
        }
    }
    */

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginPage() {
        var emailInput by remember { mutableStateOf("") }
        var passwordInput by remember { mutableStateOf("") }
        var resultText by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ){
                Text(
                    text = "Hi!",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = resultText,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    value = emailInput,
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                    //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    onValueChange = {
                        emailInput = it
                    },
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "Enter your e-mail") },
                    modifier = Modifier.padding(5.dp)
                        .size(width = 280.dp, height = 65.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = passwordInput,
                    leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = "emailIcon") },
                    //trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    onValueChange = {
                        passwordInput = it
                    },
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Enter your Password") },
                    modifier = Modifier.padding(5.dp)
                        .size(width = 280.dp, height = 65.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )

                )
                FilledTonalButton(
                    onClick = {
                        auth.signInWithEmailAndPassword(emailInput, passwordInput)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    goToHomepage()
                                } else {
                                    val exception = task.exception
                                    if (exception != null) {
                                        // Handle specific exceptions here if needed
                                        val errorMessage = exception.message ?: "Unknown error occurred"
                                        resultText = errorMessage
                                    }
                                }
                            }
                    },
                    Modifier.padding(10.dp)
                ){
                    Text("Log In")
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp, // You can adjust the thickness of the line as needed
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Don't you have an account?",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                OutlinedButton(
                    onClick = {goToSignIn()},
                ) {
                    Text("Join!")
                }
            }
        }
    }

    private fun goToHomepage(): Unit{
        val intent = Intent(this@LoginActivity, HomepageActivity::class.java)
        startActivity(intent)
    }

    private fun goToSignIn(): Unit{
        val intent = Intent(this@LoginActivity, SigninActivity::class.java)
        startActivity(intent)
    }

    @Preview(showBackground = true)
    @Composable
    fun LoginPagePreview() {
        ToneTheme {
            LoginPage()
        }
    }
}
