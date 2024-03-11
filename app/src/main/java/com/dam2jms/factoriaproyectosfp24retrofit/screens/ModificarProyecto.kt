package com.dam2jms.factoriaproyectosfp24retrofit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2jms.factoriaproyectosfp24retrofit.models.ViewModelHome
import com.dam2jms.factoriaproyectosfp24retrofit.states.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarProyecto(navController: NavController, mvvm: ViewModelHome) {
    val uiState by mvvm.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Factoria de Proyectos") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        },
    ) { paddingValues ->
        modificarProyectoBody(modifier = Modifier.padding(paddingValues), navController, mvvm, uiState)
    }
}

@Composable
fun modificarProyectoBody(modifier: Modifier, navController: NavController, mvvm: ViewModelHome, uiState: UiState) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = uiState.ID,
            onValueChange = { mvvm.onChangeModificar(it, uiState.nombreProyecto, uiState.descripcion, uiState.estado, uiState.contacto) },
            label = { Text("ID del Proyecto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = uiState.nombreProyecto,
            onValueChange = { mvvm.onChangeModificar(uiState.ID, it, uiState.descripcion, uiState.estado, uiState.contacto) },
            label = { Text("Nombre del Proyecto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = uiState.descripcion,
            onValueChange = { mvvm.onChangeModificar(uiState.ID, uiState.nombreProyecto, it, uiState.estado, uiState.contacto) },
            label = { Text("Descripción del Proyecto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = uiState.estado,
            onValueChange = { mvvm.onChangeModificar(uiState.ID, uiState.nombreProyecto, uiState.descripcion, it, uiState.contacto) },
            label = { Text("Estado del Proyecto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = uiState.contacto,
            onValueChange = { mvvm.onChangeModificar(uiState.ID, uiState.nombreProyecto, uiState.descripcion, uiState.estado, it) },
            label = { Text("Contacto del Proyecto") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                val proyectoModificado = Proyecto(
                    id = uiState.ID,
                    nombreProyecto = uiState.nombreProyecto,
                    descripcion = uiState.descripcion,
                    estado = uiState.estado,
                    contacto = uiState.contacto
                )

                mvvm.modificarProyecto(uiState.ID, proyectoModificado)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Modificar Proyecto")
        }
    }
}
