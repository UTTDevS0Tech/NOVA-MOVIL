package com.example.nova_movil.ui.tipo_servicio

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
import com.example.nova_movil.data.remote.dto.TipoServicioDto

private val ScreenBg = Color(0xFFF7F1E3)
private val MainCream = Color(0xFFF8F1E3)
private val CardBrown = Color(0xFFD19E6A)
private val TextBrown = Color(0xFF6E5443)
private val GreenSoft = Color(0xFFC3CE9C)
private val PinkSoft = Color(0xFFF5DCDC)
private val WhiteSoft = Color(0xFFFDFBF7)
private val RedSoft = Color(0xFFE39A9A)

@Composable
fun AdminTipoServicioRoute(
    viewModel: AdminTipoServicioViewModel,
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AdminTipoServicioScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryClick = viewModel::loadTiposServicio,
        onToggleClick = viewModel::toggleTipoServicio,
        onDeleteClick = viewModel::deleteTipoServicio,
        onCreateClick = viewModel::createTipoServicio,
        onUpdateClick = viewModel::updateTipoServicio,
        onFilterChange = viewModel::setFilter
    )
}

@Composable
fun AdminTipoServicioScreen(
    uiState: AdminTipoServicioUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onToggleClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onCreateClick: (String, Double, String?, Boolean, Int, Int) -> Unit,
    onUpdateClick: (Int, String, Double, String?, Boolean, Int, Int) -> Unit,
    onFilterChange: (TipoServicioFilter) -> Unit
) {
    var idAEliminar by remember { mutableIntStateOf(-1) }
    var nombreAEliminar by remember { mutableStateOf("") }

    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    var editId by remember { mutableIntStateOf(-1) }
    var editNombre by remember { mutableStateOf("") }
    var editDescripcion by remember { mutableStateOf("") }
    var editPrecio by remember { mutableStateOf("0") }
    var editTiempo by remember { mutableStateOf("1") }
    var editServicioId by remember { mutableStateOf("1") }
    var editActivo by remember { mutableStateOf(true) }

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
                            contentDescription = null,
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
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text("Recargar")
                    }
                }
            }

            Text(
                text = "Tipos de Servicio",
                color = TextBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Consulta y administra tus tipos de servicio",
                color = TextBrown.copy(alpha = 0.75f),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.size(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = uiState.selectedFilter == TipoServicioFilter.TODOS,
                    onClick = { onFilterChange(TipoServicioFilter.TODOS) },
                    label = { Text("Todos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CardBrown.copy(alpha = 0.25f)
                    )
                )

                FilterChip(
                    selected = uiState.selectedFilter == TipoServicioFilter.ACTIVOS,
                    onClick = { onFilterChange(TipoServicioFilter.ACTIVOS) },
                    label = { Text("Activos") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GreenSoft
                    )
                )

                FilterChip(
                    selected = uiState.selectedFilter == TipoServicioFilter.INACTIVOS,
                    onClick = { onFilterChange(TipoServicioFilter.INACTIVOS) },
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
                        items(uiState.filteredTiposServicio) { item ->
                            TipoServicioCard(
                                item = item,
                                onEditClick = {
                                    editId = item.id ?: -1
                                    editNombre = item.nombre.orEmpty()
                                    editDescripcion = item.descripcion.orEmpty()
                                    editPrecio = (item.precio ?: 0.0).toString()
                                    editTiempo = (item.tiempo_estimado ?: 1).toString()
                                    editServicioId = (item.servicio_id ?: 1).toString()
                                    editActivo = item.isActivo
                                    showEditDialog = true
                                },
                                onToggleClick = {
                                    item.id?.let(onToggleClick)
                                },
                                onDeleteClick = {
                                    idAEliminar = item.id ?: -1
                                    nombreAEliminar = item.nombre ?: "este tipo de servicio"
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        TipoServicioFormDialog(
            title = "Crear tipo de servicio",
            initialNombre = "",
            initialDescripcion = "",
            initialPrecio = "0",
            initialTiempo = "1",
            initialServicioId = "1",
            initialActivo = true,
            confirmText = "Crear",
            onDismiss = { showCreateDialog = false },
            onConfirm = { nombre, descripcion, precio, tiempo, servicioId, activo ->
                onCreateClick(nombre, precio, descripcion, activo, tiempo, servicioId)
                showCreateDialog = false
            }
        )
    }

    if (showEditDialog && editId != -1) {
        TipoServicioFormDialog(
            title = "Editar tipo de servicio",
            initialNombre = editNombre,
            initialDescripcion = editDescripcion,
            initialPrecio = editPrecio,
            initialTiempo = editTiempo,
            initialServicioId = editServicioId,
            initialActivo = editActivo,
            confirmText = "Guardar",
            onDismiss = { showEditDialog = false },
            onConfirm = { nombre, descripcion, precio, tiempo, servicioId, activo ->
                onUpdateClick(editId, nombre, precio, descripcion, activo, tiempo, servicioId)
                showEditDialog = false
            }
        )
    }

    if (idAEliminar != -1) {
        AlertDialog(
            onDismissRequest = {
                idAEliminar = -1
                nombreAEliminar = ""
            },
            title = { Text("Eliminar tipo de servicio") },
            text = { Text("¿Seguro que quieres eliminar \"$nombreAEliminar\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(idAEliminar)
                        idAEliminar = -1
                        nombreAEliminar = ""
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFB04B4B))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        idAEliminar = -1
                        nombreAEliminar = ""
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun TipoServicioCard(
    item: com.example.nova_movil.data.remote.dto.TipoServicioDto,
    onEditClick: () -> Unit,
    onToggleClick: () -> Unit,
    onDeleteClick: () -> Unit
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

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Precio: \$${item.precio ?: 0.0}",
                color = TextBrown,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Tiempo estimado: ${item.tiempo_estimado ?: 0} min",
                color = TextBrown,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Servicio ID: ${item.servicio_id ?: 0}",
                color = TextBrown.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = if (item.isActivo) "Activo" else "Inactivo",
                color = if (item.isActivo) Color(0xFF4F7A3D) else Color(0xFFB04B4B),
                fontWeight = FontWeight.Bold
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
                        contentDescription = null,
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
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(if (item.isActivo) "Desactivar" else "Activar")
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
                        contentDescription = null,
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
private fun TipoServicioFormDialog(
    title: String,
    initialNombre: String,
    initialDescripcion: String,
    initialPrecio: String,
    initialTiempo: String,
    initialServicioId: String,
    initialActivo: Boolean,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String?, Double, Int, Int, Boolean) -> Unit
) {
    var nombre by remember { mutableStateOf(initialNombre) }
    var descripcion by remember { mutableStateOf(initialDescripcion) }
    var precio by remember { mutableStateOf(initialPrecio) }
    var tiempo by remember { mutableStateOf(initialTiempo) }
    var servicioId by remember { mutableStateOf(initialServicioId) }
    var activo by remember { mutableStateOf(initialActivo) }

    val parsedPrecio = precio.toDoubleOrNull()
    val parsedTiempo = tiempo.toIntOrNull()
    val parsedServicioId = servicioId.toIntOrNull()

    val isValid = nombre.trim().isNotEmpty() &&
            parsedPrecio != null &&
            parsedTiempo != null &&
            parsedTiempo > 0 &&
            parsedServicioId != null

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
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tiempo,
                    onValueChange = { tiempo = it },
                    label = { Text("Tiempo estimado (min)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = servicioId,
                    onValueChange = { servicioId = it },
                    label = { Text("Servicio ID") },
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
                onClick = {
                    onConfirm(
                        nombre.trim(),
                        descripcion.trim().ifBlank { null },
                        parsedPrecio ?: 0.0,
                        parsedTiempo ?: 1,
                        parsedServicioId ?: 1,
                        activo
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