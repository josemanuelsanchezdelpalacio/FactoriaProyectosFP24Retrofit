package com.dam2jms.factoriaproyectosfp24retrofit.models

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam2jms.factoriaproyectosfp24retrofit.screens.Proyecto
import com.dam2jms.factoriaproyectosfp24retrofit.screens.RetrofitServiceFactory
import com.dam2jms.factoriaproyectosfp24retrofit.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ViewModelHome : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val service = RetrofitServiceFactory.makeRetrofitService()

    fun onChangeAñadir(proyecto: String, centro: String, responsable: String) {
        _uiState.update { currentState ->
            currentState.copy(proyecto = proyecto, centro = centro, responsable = responsable)
        }
    }

    suspend fun agregarProyecto(uiState: UiState) {
        val proyecto = Proyecto(
            proyecto = uiState.proyecto,
            centro = uiState.centro,
            responsable = uiState.responsable
        )

        try {
            service.createProyecto(proyecto)
        } catch (e: Exception) {
            // Handle the exception
        }
    }
}