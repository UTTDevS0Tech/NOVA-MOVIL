package com.example.nova_movil.ui.servicios

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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.ToggleOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nova_movil.data.remote.dto.ServicioDto

private val ScreenBg = Color(0xFFF7F1E3)
private val MainCream = Color(0xFFF8F1E3)
private val CardBrown = Color(0xFFD19E6A)
private val TextBrown = Color(0xFF6E5443)
private val GreenSoft = Color(0xFFC3CE9C)
private val PinkSoft = Color(0xFFF5DCDC)
private val WhiteSoft = Color(0xFFFDFBF7)
private val RedSoft = Color(0xFFE39A9A)

@Composable
fun AdminServiciosRoute(
    viewModel: AdminServiciosViewModel,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AdminServiciosScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryClick = viewModel::loadServicios,
        onToggleClick = viewModel::toggleServicio,
        onDeleteClick = viewModel::deleteServicio,
        onCreateClick = viewModel::createServicio,
        onUpdateClick = viewModel::updateServicio,
        onFilterChange = viewModel::setFilter
    )
}

@Composable
fun AdminServiciosScreen(
    uiState: AdminServiciosUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onToggleClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onCreateClick: (String, Boolean) -> Unit,
    onUpdateClick: (Int, String, Boolean) -> Unit,
    onFilterChange: (ServicioFilter) -> Unit
) {
    var servicioIdAEliminar by remember { mutableIntStateOf(-1) }
    var nombreServicioAEliminar by remember { mutableStateOf("") }

    var showCreateDialog by remember { mutableStateOf(false) }

    var showEditDialog by remember { mutableStateOf(false) }
    var servicioAEditarId by remember { mutableIntStateOf(-1) }
    var servicioAEditarNombre by remember { mutableStateOf("") }
    var servicioAEditarActivo by remember { mutableStateOf(true) }

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
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Crear",
                            modifier = Modifier.size(18.dp)
                        )
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
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Recargar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text("Recargar")
                    }
                }
            }

            Text(
                text = "Administrar Servicios",
                color = TextBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Consulta y administra tus servicios",
                color = TextBrown.copy(alpha = 0.75f),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.size(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = uiState.selectedFilter == ServicioFilter.TODOS,
                    onClick = { onFilterChange(ServicioFilter.TODOS) },
                    label = { Text("Todos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CardBrown.copy(alpha = 0.25f)
                    )
                )

                FilterChip(
                    selected = uiState.selectedFilter == ServicioFilter.ACTIVOS,
                    onClick = { onFilterChange(ServicioFilter.ACTIVOS) },
                    label = { Text("Activos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GreenSoft
                    )
                )

                FilterChip(
                    selected = uiState.selectedFilter == ServicioFilter.INACTIVOS,
                    onClick = { onFilterChange(ServicioFilter.INACTIVOS) },
                    label = { Text("Inactivos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PinkSoft
                    )
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            uiState.actionMessage?.let { mensaje ->
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
                        items(uiState.filteredServicios) { servicio ->
                            ServicioAdminCard(
                                servicio = servicio,
                                onEditClick = {
                                    servicioAEditarId = servicio.id ?: -1
                                    servicioAEditarNombre = servicio.nombre.orEmpty()
                                    servicioAEditarActivo = servicio.isActivo
                                    showEditDialog = true
                                },
                                onToggleClick = {
                                    servicio.id?.let(onToggleClick)
                                },
                                onDeleteClick = {
                                    servicioIdAEliminar = servicio.id ?: -1
                                    nombreServicioAEliminar = servicio.nombre ?: "este servicio"
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        ServicioFormDialog(
            title = "Crear servicio",
            initialNombre = "",
            initialActivo = true,
            confirmText = "Crear",
            onDismiss = { showCreateDialog = false },
            onConfirm = { nombre, activo ->
                onCreateClick(nombre, activo)
                showCreateDialog = false
            }
        )
    }

    if (showEditDialog && servicioAEditarId != -1) {
        ServicioFormDialog(
            title = "Editar servicio",
            initialNombre = servicioAEditarNombre,
            initialActivo = servicioAEditarActivo,
            confirmText = "Guardar",
            onDismiss = { showEditDialog = false },
            onConfirm = { nombre, activo ->
                onUpdateClick(servicioAEditarId, nombre, activo)
                showEditDialog = false
            }
        )
    }

    if (servicioIdAEliminar != -1) {
        AlertDialog(
            onDismissRequest = {
                servicioIdAEliminar = -1
                nombreServicioAEliminar = ""
            },
            title = { Text("Eliminar servicio") },
            text = { Text("¿Seguro que quieres eliminar \"$nombreServicioAEliminar\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(servicioIdAEliminar)
                        servicioIdAEliminar = -1
                        nombreServicioAEliminar = ""
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFB04B4B))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        servicioIdAEliminar = -1
                        nombreServicioAEliminar = ""
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun ServicioAdminCard(
    servicio: com.example.nova_movil.data.remote.dto.ServicioDto,
    onEditClick: () -> Unit,
    onToggleClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val isActivo = servicio.isActivo

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = WhiteSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = servicio.nombre ?: "Sin nombre",
                color = TextBrown,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "ID: ${servicio.id ?: 0}",
                color = TextBrown.copy(alpha = 0.75f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = if (isActivo) "Activo" else "Inactivo",
                color = if (isActivo) Color(0xFF4F7A3D) else Color(0xFFB04B4B),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.size(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onEditClick,
                    colors = ButtonDefaults.buttonColors(containerColor = CardBrown),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text("Editar")
                }

                Button(
                    onClick = onToggleClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenSoft,
                        contentColor = TextBrown
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ToggleOn,
                        contentDescription = "Cambiar estado",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(if (isActivo) "Desactivar" else "Activar")
                }

                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RedSoft,
                        contentColor = WhiteSoft
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}

@Composable
private fun ServicioFormDialog(
    title: String,
    initialNombre: String,
    initialActivo: Boolean,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: (String, Boolean) -> Unit
) {
    var nombre by remember { mutableStateOf(initialNombre) }
    var activo by remember { mutableStateOf(initialActivo) }
    val isValid = nombre.trim().isNotEmpty()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del servicio") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Activo", color = TextBrown, fontWeight = FontWeight.Medium)
                    Switch(
                        checked = activo,
                        onCheckedChange = { activo = it }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(nombre.trim(), activo) },
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