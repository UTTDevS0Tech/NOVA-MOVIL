package com.example.nova_movil.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.nova_movil.R

private val BackgroundCream = Color(0xFFF3E8D6)
private val SoftCream = Color(0xFFF8F1E3)
private val LightGreen = Color(0xFFDDE5C8)
private val SoftPeach = Color(0xFFE6C29E)
private val AccentBrown = Color(0xFFC99661)
private val TitleBrown = Color(0xFF6A5142)
private val TextMuted = Color(0xFF8B7768)
private val FieldBg = Color(0xFFF5F1EA)
private val WhiteSoft = Color(0xFFFDFBF7)
private val ErrorColor = Color(0xFFB85C5C)

@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToAdminDashboard) {
        if (uiState.navigateToAdminDashboard) {
            onNavigateToHome()
            viewModel.consumeAdminNavigation()
        }
    }

    LoginScreen(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
        onLoginClick = viewModel::onLoginClick,
        onRegisterClick = onNavigateToRegister
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundCream
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            LightGreen.copy(alpha = 0.75f),
                            BackgroundCream,
                            SoftPeach.copy(alpha = 0.45f)
                        )
                    )
                )
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = SoftCream),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 28.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        color = TitleBrown,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Accede con tu correo y contraseña",
                        color = TextMuted,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Correo Electrónico",
                        color = TitleBrown,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "correo@ejemplo.com",
                                color = TextMuted.copy(alpha = 0.7f)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "Correo",
                                tint = AccentBrown
                            )
                        },
                        isError = uiState.emailError != null,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = FieldBg,
                            unfocusedContainerColor = FieldBg,
                            disabledContainerColor = FieldBg,
                            errorContainerColor = FieldBg,
                            focusedBorderColor = AccentBrown,
                            unfocusedBorderColor = Color.Transparent,
                            errorBorderColor = ErrorColor,
                            cursorColor = TitleBrown,
                            focusedTextColor = TitleBrown,
                            unfocusedTextColor = TitleBrown
                        )
                    )

                    uiState.emailError?.let {
                        Text(
                            text = it,
                            color = ErrorColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Contraseña",
                        color = TitleBrown,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "••••••••",
                                color = TextMuted.copy(alpha = 0.7f)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = "Contraseña",
                                tint = AccentBrown
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = onTogglePasswordVisibility) {
                                Icon(
                                    imageVector = if (uiState.passwordVisible) {
                                        Icons.Outlined.VisibilityOff
                                    } else {
                                        Icons.Outlined.Visibility
                                    },
                                    contentDescription = if (uiState.passwordVisible) {
                                        "Ocultar contraseña"
                                    } else {
                                        "Mostrar contraseña"
                                    },
                                    tint = AccentBrown
                                )
                            }
                        },
                        isError = uiState.passwordError != null,
                        visualTransformation = if (uiState.passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = FieldBg,
                            unfocusedContainerColor = FieldBg,
                            disabledContainerColor = FieldBg,
                            errorContainerColor = FieldBg,
                            focusedBorderColor = AccentBrown,
                            unfocusedBorderColor = Color.Transparent,
                            errorBorderColor = ErrorColor,
                            cursorColor = TitleBrown,
                            focusedTextColor = TitleBrown,
                            unfocusedTextColor = TitleBrown
                        )
                    )

                    uiState.passwordError?.let {
                        Text(
                            text = it,
                            color = ErrorColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    uiState.loginError?.let {
                        Text(
                            text = it,
                            color = ErrorColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = onLoginClick,
                        enabled = uiState.isFormValid && !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentBrown,
                            contentColor = WhiteSoft,
                            disabledContainerColor = AccentBrown.copy(alpha = 0.5f),
                            disabledContentColor = WhiteSoft.copy(alpha = 0.8f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                color = WhiteSoft,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Entrar",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }

                    TextButton(
                        onClick = onRegisterClick,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "¿No tienes cuenta? Crear cuenta",
                            color = TitleBrown
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        LightGreen.copy(alpha = 0.95f),
                        BackgroundCream.copy(alpha = 0.90f),
                        SoftPeach.copy(alpha = 0.75f)
                    )
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.nova_logo),
            contentDescription = "Logo Nova",
            modifier = Modifier
                .height(100.dp)
                .align(Alignment.Center)
        )
    }
}

