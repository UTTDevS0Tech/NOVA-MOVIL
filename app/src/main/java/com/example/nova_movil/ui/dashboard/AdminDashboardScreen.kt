package com.example.nova_movil.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DashboardBackground = Color(0xFFF7F1E3)
private val SidebarGreen = Color(0xFFD8E0BF)
private val MainCream = Color(0xFFF8F1E3)
private val CardBrown = Color(0xFFD19E6A)
private val CardGreen = Color(0xFFC3CE9C)
private val TextBrown = Color(0xFF6E5443)
private val SoftPink = Color(0xFFF5DCDC)
private val WhiteSoft = Color(0xFFFDFBF7)

@Composable
fun AdminDashboardScreen(
    onLogoutClick: () -> Unit = {},
    onManageServicesClick: () -> Unit = {},
    onManageServiceTypesClick: () -> Unit = {},
    onManageStaffClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DashboardBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(12.dp)
                .background(
                    color = MainCream,
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .width(22.dp)
                    .fillMaxSize()
                    .background(
                        color = SidebarGreen,
                        shape = RoundedCornerShape(
                            topStart = 28.dp,
                            bottomStart = 28.dp
                        )
                    )
            ) {}

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onLogoutClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SoftPink,
                            contentColor = Color(0xFFB04B4B)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = "Cerrar sesión",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cerrar sesión",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text = "Accesos rápidos",
                    color = TextBrown,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Gestiona el sistema con facilidad",
                    color = TextBrown.copy(alpha = 0.75f),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                DashboardActionCard(
                    title = "Administrar Servicios",
                    description = "Agrega nuevos servicios al catálogo de la estética.",
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Inventory2,
                            contentDescription = null,
                            tint = WhiteSoft,
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    containerColor = CardBrown,
                    contentColor = WhiteSoft,
                    onClick = onManageServicesClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                DashboardActionCard(
                    title = "Administrar Tipos de Servicio",
                    description = "Administra los tipos de servicio disponibles.",
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Sell,
                            contentDescription = null,
                            tint = WhiteSoft,
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    containerColor = CardBrown,
                    contentColor = WhiteSoft,
                    onClick = onManageServiceTypesClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                DashboardActionCard(
                    title = "Administrar Personal",
                    description = "Administra personal nuevo y amplía tu equipo de trabajo.",
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Group,
                            contentDescription = null,
                            tint = TextBrown,
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    containerColor = CardGreen,
                    contentColor = TextBrown,
                    onClick = onManageStaffClick
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun DashboardActionCard(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 500.dp)
            .height(185.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            icon()

            Column {
                Text(
                    text = title,
                    color = contentColor,
                    fontSize = 17.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = description,
                    color = contentColor.copy(alpha = 0.95f),
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }
        }
    }
}