package com.dam2jms.factoriaproyectosfp24retrofit.models

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import com.dam2jms.factoriaproyectosfp24retrofit.screens.Proyecto
import com.dam2jms.factoriaproyectosfp24retrofit.states.UiState
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await


/*class ViewModelHome : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _agregarProyectoResult = MutableLiveData<Boolean>()
    val agregarProyectoResult: LiveData<Boolean> get() = _agregarProyectoResult

    val service = RetrofitServiceFactory.makeRetrofitService()

    fun onChangeAñadir(proyecto: String, centro: String, responsable: String) {
        _uiState.update { currentState ->
            currentState.copy(proyecto = proyecto, centro = centro, responsable = responsable)
        }
    }


    suspend fun agregarProyecto(proyecto: Proyecto) {
        try {
            service.createProyecto(proyecto)
            _agregarProyectoResult.postValue(true)
        } catch (e: Exception) {
            _agregarProyectoResult.postValue(false)
            // Manejar errores
        }
    }
}*/

class ViewModelHome : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    // Inicializo el Firestore
    private val db = Firebase.firestore

    fun onChangeAñadir(proyecto: String, centro: String, responsable: String) {
        // Actualiza el estado directamente utilizando la función update
        _uiState.update { currentState ->
            currentState.copy(proyecto = proyecto, centro = centro, responsable = responsable)
        }
    }

    suspend fun leerProyectos(): List<Proyecto> {
        return try {
            val proyectos = Firebase.firestore.collection("proyectos").get().await()
            proyectos.toObjects<Proyecto>()
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
    }


    fun agregarProyecto(proyecto: Proyecto) {
        db.collection("proyectos")
            .add(proyecto)
            .addOnSuccessListener {
                // Puedes realizar acciones adicionales después de agregar un proyecto (opcional)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}