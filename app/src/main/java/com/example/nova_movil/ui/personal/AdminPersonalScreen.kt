package com.example.nova_movil.ui.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nova_movil.data.remote.dto.PersonalDto

private val ScreenBg = Color(0xFFF7F1E3)
private val MainCream = Color(0xFFF8F1E3)
private val CardBrown = Color(0xFFD19E6A)
private val TextBrown = Color(0xFF6E5443)
private val GreenSoft = Color(0xFFC3CE9C)
private val PinkSoft = Color(0xFFF5DCDC)
private val WhiteSoft = Color(0xFFFDFBF7)

@Composable
fun AdminPersonalRoute(
    viewModel: AdminPersonalViewModel,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AdminPersonalScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryClick = viewModel::load,
        onCreateClick = viewModel::createPersonal,
        onToggleClick = viewModel::togglePersonal
    )
}

@Composable
fun AdminPersonalScreen(
    uiState: AdminPersonalUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onCreateClick: (String, String?, Int) -> Unit,
    onToggleClick: (Int) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ScreenBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(16.dp)
                .background(MainCream, RoundedCornerShape(28.dp))
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextBrown
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { showCreateDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenSoft,
                            contentColor = TextBrown
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text("Nuevo")
                    }

                    Button(
                        onClick = onRetryClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PinkSoft,
                            contentColor = TextBrown
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Outlined.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text("Recargar")
                    }
                }
            }

            Text(
                text = "Personal",
                color = TextBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Consulta y administra estilistas",
                color = TextBrown.copy(alpha = 0.75f),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.size(12.dp))

            uiState.message?.let { mensaje ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = GreenSoft),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = mensaje,
                        color = TextBrown,
                        modifier = Modifier.padding(14.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = CardBrown)
                    }
                }

                uiState.error != null -> {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PinkSoft),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = uiState.error,
                                color = TextBrown,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(uiState.personales) { item ->
                            PersonalCard(
                                item = item,
                                onToggleClick = {
                                    item.user_id?.let(onToggleClick)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        PersonalFormDialog(
            title = "Crear estilista",
            initialNombre = "",
            initialDescripcion = "",
            initialUserId = "1",
            confirmText = "Crear",
            onDismiss = { showCreateDialog = false },
            onConfirm = { nombre, descripcion, userId ->
                onCreateClick(nombre, descripcion, userId)
                showCreateDialog = false
            }
        )
    }
}

@Composable
private fun PersonalCard(
    item: PersonalDto,
    onToggleClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = WhiteSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = item.nombre ?: "Sin nombre",
                color = TextBrown,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = item.descripcion ?: "Sin descripción",
                color = TextBrown.copy(alpha = 0.75f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Horarios",
                color = TextBrown,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(6.dp))

            if (item.horarios.isNullOrEmpty()) {
                Text(
                    text = "Sin horarios registrados",
                    color = TextBrown.copy(alpha = 0.7f)
                )
            } else {
                item.horarios.forEach { horario ->
                    Text(
                        text = "${horario.dia ?: "Día"}: ${horario.inicio ?: "--"} - ${horario.fin ?: "--"}",
                        color = TextBrown.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.size(14.dp))

            Button(
                onClick = onToggleClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenSoft,
                    contentColor = TextBrown
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Cambiar estado")
            }
        }
    }
}

@Composable
private fun PersonalFormDialog(
    title: String,
    initialNombre: String,
    initialDescripcion: String,
    initialUserId: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String?, Int) -> Unit
) {
    var nombre by remember { mutableStateOf(initialNombre) }
    var descripcion by remember { mutableStateOf(initialDescripcion) }
    var userId by remember { mutableStateOf(initialUserId) }

    val parsedUserId = userId.toIntOrNull()
    val isValid = nombre.trim().isNotEmpty() && parsedUserId != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text("User ID") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        nombre.trim(),
                        descripcion.trim().ifBlank { null },
                        parsedUserId ?: 1
                    )
                },
                enabled = isValid
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}