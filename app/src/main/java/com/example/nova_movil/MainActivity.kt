package com.example.nova_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.example.nova_movil.data.local.SessionManager
import com.example.nova_movil.data.remote.ApiClient
import com.example.nova_movil.data.repository.AuthRepository
import com.example.nova_movil.data.repository.PersonalRepository
import com.example.nova_movil.data.repository.ServicioRepository
import com.example.nova_movil.data.repository.TipoServicioRepository
import com.example.nova_movil.ui.auth.LoginRoute
import com.example.nova_movil.ui.auth.LoginViewModel
import com.example.nova_movil.ui.auth.LoginViewModelFactory
import com.example.nova_movil.ui.dashboard.AdminDashboardScreen
import com.example.nova_movil.ui.personal.AdminPersonalRoute
import com.example.nova_movil.ui.personal.AdminPersonalViewModel
import com.example.nova_movil.ui.personal.AdminPersonalViewModelFactory
import com.example.nova_movil.ui.servicios.AdminServiciosRoute
import com.example.nova_movil.ui.servicios.AdminServiciosViewModel
import com.example.nova_movil.ui.servicios.AdminServiciosViewModelFactory
import com.example.nova_movil.ui.tipo_servicio.AdminTipoServicioRoute
import com.example.nova_movil.ui.tipo_servicio.AdminTipoServicioViewModel
import com.example.nova_movil.ui.tipo_servicio.AdminTipoServicioViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var adminServiciosViewModel: AdminServiciosViewModel
    private lateinit var adminTipoServicioViewModel: AdminTipoServicioViewModel
    private lateinit var adminPersonalViewModel: AdminPersonalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(applicationContext)
        val authApi = ApiClient.provideAuthApi(applicationContext)
        val authRepository = AuthRepository(authApi)
        val loginFactory = LoginViewModelFactory(authRepository, sessionManager)
        loginViewModel = ViewModelProvider(this, loginFactory)[LoginViewModel::class.java]

        val servicioApi = ApiClient.provideServicioApi(applicationContext)
        val servicioRepository = ServicioRepository(servicioApi)
        val serviciosFactory = AdminServiciosViewModelFactory(servicioRepository)
        adminServiciosViewModel =
            ViewModelProvider(this, serviciosFactory)[AdminServiciosViewModel::class.java]

        val tipoServicioApi = ApiClient.provideTipoServicioApi(applicationContext)
        val tipoServicioRepository = TipoServicioRepository(tipoServicioApi)
        val tipoServicioFactory = AdminTipoServicioViewModelFactory(tipoServicioRepository)
        adminTipoServicioViewModel =
            ViewModelProvider(this, tipoServicioFactory)[AdminTipoServicioViewModel::class.java]

        val personalApi = ApiClient.providePersonalApi(applicationContext)
        val personalRepository = PersonalRepository(personalApi)
        val personalFactory = AdminPersonalViewModelFactory(personalRepository)
        adminPersonalViewModel =
            ViewModelProvider(this, personalFactory)[AdminPersonalViewModel::class.java]

        setContent {
            var currentScreen by remember { mutableStateOf("login") }

            when (currentScreen) {
                "login" -> {
                    LoginRoute(
                        viewModel = loginViewModel,
                        onNavigateToHome = {
                            currentScreen = "admin_dashboard"
                        },
                        onNavigateToRegister = {}
                    )
                }

                "admin_dashboard" -> {
                    AdminDashboardScreen(
                        onLogoutClick = {
                            currentScreen = "login"
                        },
                        onManageServicesClick = {
                            currentScreen = "admin_servicios"
                        },
                        onManageServiceTypesClick = {
                            currentScreen = "admin_tipo_servicio"
                        },
                        onManageStaffClick = {
                            currentScreen = "admin_personal"
                        }
                    )
                }

                "admin_servicios" -> {
                    AdminServiciosRoute(
                        viewModel = adminServiciosViewModel,
                        onBackClick = {
                            currentScreen = "admin_dashboard"
                        }
                    )
                }

                "admin_tipo_servicio" -> {
                    AdminTipoServicioRoute(
                        viewModel = adminTipoServicioViewModel,
                        onBackClick = {
                            currentScreen = "admin_dashboard"
                        }
                    )
                }

                "admin_personal" -> {
                    AdminPersonalRoute(
                        viewModel = adminPersonalViewModel,
                        onBackClick = {
                            currentScreen = "admin_dashboard"
                        }
                    )
                }
            }
        }
    }
}