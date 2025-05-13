package com.example.ibogorkab.ui

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
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
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToVerification: () -> Unit = {}
) {
    // States
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }

    // Validation states
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var termsError by remember { mutableStateOf<String?>(null) }

    // Define exact colors from the design
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val Yellow = Color(0xFFCCE600) // Approximation of the yellow at the bottom
    val white = Color.White
    val ErrorRed = Color(0xFFB00020)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Define text styles with Inter font
    val titleStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Black,
        fontFamily = interFontFamily,
        textAlign = TextAlign.Center
    )

    val labelStyle = TextStyle(
        fontSize = 12.sp,
        color = Black,
        fontFamily = interFontFamily
    )

    val errorStyle = TextStyle(
        fontSize = 10.sp,
        color = ErrorRed,
        fontFamily = interFontFamily
    )

    // Validation functions
    fun validateName(): Boolean {
        return when {
            name.isEmpty() -> {
                nameError = "Nama tidak boleh kosong"
                false
            }
            name.length < 3 -> {
                nameError = "Nama minimal 3 karakter"
                false
            }
            else -> {
                nameError = null
                true
            }
        }
    }

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

    fun validatePhone(): Boolean {
        return when {
            phoneNumber.isEmpty() -> {
                phoneError = "Nomor telepon tidak boleh kosong"
                false
            }
            !phoneNumber.all { it.isDigit() } -> {
                phoneError = "Nomor telepon hanya boleh angka"
                false
            }
            phoneNumber.length < 10 || phoneNumber.length > 13 -> {
                phoneError = "Nomor telepon harus 10-13 digit"
                false
            }
            else -> {
                phoneError = null
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

    fun validateConfirmPassword(): Boolean {
        return when {
            confirmPassword.isEmpty() -> {
                confirmPasswordError = "Konfirmasi password tidak boleh kosong"
                false
            }
            confirmPassword != password -> {
                confirmPasswordError = "Konfirmasi password tidak sama"
                false
            }
            else -> {
                confirmPasswordError = null
                true
            }
        }
    }

    fun validateTerms(): Boolean {
        return when {
            !termsAccepted -> {
                termsError = "Anda harus menyetujui Syarat & Ketentuan"
                false
            }
            else -> {
                termsError = null
                true
            }
        }
    }

    fun validateAndRegister() {
        val isNameValid = validateName()
        val isEmailValid = validateEmail()
        val isPhoneValid = validatePhone()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()
        val isTermsAccepted = validateTerms()

        if (isNameValid && isEmailValid && isPhoneValid &&
            isPasswordValid && isConfirmPasswordValid && isTermsAccepted) {
            onNavigateToVerification()
        }
    }

    // Create scroll state
    val scrollState = rememberScrollState()

    // Gradient background for the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkGreen,
                        LightGreen,
                        Yellow
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // White card for form elements
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .verticalScroll(scrollState), // Add scrolling capability
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Register Text
                Text(
                    text = "Registrasi",
                    style = titleStyle,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                // Nama Field
                Text(
                    text = "Nama",
                    style = labelStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (nameError != null) validateName()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(bottom = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (nameError != null) ErrorRed else Color.LightGray,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = if (nameError != null) ErrorRed else DarkGreen
                    ),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    ),
                    singleLine = true,
                    isError = nameError != null
                )

                // Name error message
                if (nameError != null) {
                    Text(
                        text = nameError!!,
                        style = errorStyle,
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Email Field
                Text(
                    text = "Email",
                    style = labelStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (emailError != null) validateEmail()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(bottom = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (emailError != null) ErrorRed else Color.LightGray,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = if (emailError != null) ErrorRed else DarkGreen
                    ),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    ),
                    singleLine = true,
                    isError = emailError != null
                )

                // Email error message
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        style = errorStyle,
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Phone Number Field
                Text(
                    text = "Nomor Telepon",
                    style = labelStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        if (phoneError != null) validatePhone()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(bottom = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (phoneError != null) ErrorRed else Color.LightGray,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = if (phoneError != null) ErrorRed else DarkGreen
                    ),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    ),
                    singleLine = true,
                    isError = phoneError != null
                )

                // Phone error message
                if (phoneError != null) {
                    Text(
                        text = phoneError!!,
                        style = errorStyle,
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Password Field
                Text(
                    text = "Password",
                    style = labelStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (passwordError != null) validatePassword()
                        if (confirmPassword.isNotEmpty()) validateConfirmPassword()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(bottom = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (passwordError != null) ErrorRed else Color.LightGray,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = if (passwordError != null) ErrorRed else DarkGreen
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
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    ),
                    singleLine = true,
                    isError = passwordError != null
                )

                // Password error message
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        style = errorStyle,
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Confirm Password Field
                Text(
                    text = "Konfirmasi Password",
                    style = labelStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        if (confirmPasswordError != null) validateConfirmPassword()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(bottom = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = if (confirmPasswordError != null) ErrorRed else Color.LightGray,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = if (confirmPasswordError != null) ErrorRed else DarkGreen
                    ),
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                painter = painterResource(
                                    id = if (confirmPasswordVisible)
                                        R.drawable.ic_visibility_on
                                    else
                                        R.drawable.ic_visibility_off
                                ),
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                tint = Color.Gray
                            )
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    ),
                    singleLine = true,
                    isError = confirmPasswordError != null
                )

                // Confirm Password error message
                if (confirmPasswordError != null) {
                    Text(
                        text = confirmPasswordError!!,
                        style = errorStyle,
                        modifier = Modifier
                            .padding(start = 4.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Terms and Conditions Checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = termsAccepted,
                        onCheckedChange = {
                            termsAccepted = it
                            if (termsError != null) validateTerms()
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkGreen,
                            uncheckedColor = if (termsError != null) ErrorRed else Color.Gray
                        )
                    )

                    Text(
                        text = "Saya sudah membaca dan setuju dengan Syarat & Ketentuan",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = interFontFamily,
                            color = if (termsError != null) ErrorRed else Black
                        )
                    )
                }

                // Terms error message
                if (termsError != null) {
                    Text(
                        text = termsError!!,
                        style = errorStyle,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Register Button
                Button(
                    onClick = { validateAndRegister() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = White
                    )
                ) {
                    Text(
                        text = "Daftar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Login option
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sudah punya akun? ",
                        color = Black,
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    )
                    TextButton(onClick = { onNavigateToLogin() }) {
                        Text(
                            text = "Masuk",
                            fontSize = 12.sp,
                            color = DarkGreen,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily
                        )
                    }
                }

                // Add extra space at bottom to ensure last items are scrollable
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}