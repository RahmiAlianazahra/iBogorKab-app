package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import android.util.Patterns

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onSuccessfulLogin: () -> Unit = {}
) {
    // States
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Validation states
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Define exact colors from the design
    val DarkGreen = Color(0xFF0C6B00)
    val DarkestGreen = Color(0xFF073301)
    val LightGreen = Color(0xFF50AD42)
    val PaleGreen = Color(0xFFEFF4ED)
    val Grey = Color(0xFF979797)
    val Yellow = Color(0xFFFDEF02)
    val white = Color.White
    val black = Color.Black
    val orangeRed = Color(0xFFFF6347)
    val ErrorRed = Color(0xFFB00020)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Define text styles with Inter font
    val headingStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = black,
        fontFamily = interFontFamily
    )

    val labelStyle = TextStyle(
        fontSize = 14.sp,
        color = black,
        fontFamily = interFontFamily
    )

    val errorStyle = TextStyle(
        fontSize = 12.sp,
        color = ErrorRed,
        fontFamily = interFontFamily
    )

    // Validation functions
    fun validateEmail(): Boolean {
        return when {
            email.isEmpty() -> {
                emailError = "Email tidak boleh kosong"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailError = "Format email tidak valid"
                false
            }
            else -> {
                emailError = null
                true
            }
        }
    }

    fun validatePassword(): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Password tidak boleh kosong"
                false
            }
            password.length < 6 -> {
                passwordError = "Password minimal 6 karakter"
                false
            }
            else -> {
                passwordError = null
                true
            }
        }
    }

    fun validateAndLogin() {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()

        if (isEmailValid && isPasswordValid) {
            onSuccessfulLogin()
        }
    }

    // Gradient background for the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkGreen,
                        DarkestGreen,
                        DarkestGreen
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo area
            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Logo box without gradient background
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Use your custom logo
                    Image(
                        painter = painterResource(id = R.drawable.ic_ibogorkab_logo),
                        contentDescription = "iBogorkab Logo",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // White background card for form elements
            Card(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = white)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    // Login Text
                    Text(
                        text = "Masuk",
                        style = headingStyle,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email Field
                    Text(
                        text = "Email",
                        style = labelStyle,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            if (emailError != null) validateEmail()
                        },
                        textStyle = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = interFontFamily
                        ),
                        modifier = Modifier
                            .fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (emailError != null) ErrorRed else Color.Gray,
                            unfocusedBorderColor = if (emailError != null) ErrorRed else Color.LightGray,
                            containerColor = white
                        ),
                        placeholder = { Text("Masukkan email anda", fontSize = 12.sp, fontFamily = interFontFamily) },
                        singleLine = true,
                        isError = emailError != null
                    )

                    // Email error message
                    if (emailError != null) {
                        Text(
                            text = emailError!!,
                            style = errorStyle,
                            modifier = Modifier
                                .padding(start = 4.dp, top = 4.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    Text(
                        text = "Kata Sandi",
                        style = labelStyle,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            if (passwordError != null) validatePassword()
                        },
                        textStyle = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = interFontFamily
                        ),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (passwordError != null) ErrorRed else Color.Gray,
                            unfocusedBorderColor = if (passwordError != null) ErrorRed else Color.LightGray,
                            containerColor = white
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible)
                                            R.drawable.ic_visibility_on
                                        else
                                            R.drawable.ic_visibility_off
                                    ),
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = Color.Gray
                                )
                            }
                        },
                        placeholder = { Text("Masukkan kata sandi anda", fontSize = 12.sp, fontFamily = interFontFamily) },
                        singleLine = true,
                        isError = passwordError != null
                    )

                    // Password error message
                    if (passwordError != null) {
                        Text(
                            text = passwordError!!,
                            style = errorStyle,
                            modifier = Modifier
                                .padding(start = 4.dp, top = 4.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }

                    // Forgot Password
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        TextButton(onClick = { /* Implement Forgot Password */ }) {
                            Text(
                                text = "Lupa Password?",
                                color = orangeRed,
                                fontFamily = interFontFamily,
                                fontSize = 12 .sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login Button
                    Button(
                        onClick = { validateAndLogin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreen
                        )
                    ) {
                        Text(
                            text = "Masuk",
                            fontSize = 14.sp,
                            color = white,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Register
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Belum punya akun? ",
                            color = black,
                            fontSize = 12.sp,
                            fontFamily = interFontFamily
                        )
                        TextButton(onClick = { onNavigateToRegister() }) {
                            Text(
                                text = "Registrasi",
                                color = DarkGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen {

    }
}