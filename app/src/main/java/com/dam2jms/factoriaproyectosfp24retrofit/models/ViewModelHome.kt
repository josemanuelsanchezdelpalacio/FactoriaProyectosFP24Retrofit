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
import kotlinx.coroutines.flow.asStateFlow
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

class ViewModelHome(private val loginViewModel: ViewModelLogin) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Inicializo el Firestore
    private val db = Firebase.firestore

    fun onChangeAñadir(nombreProyecto: String, descripcion: String, estado: String, contacto: String) {
        // Actualiza el estado directamente utilizando la función update
        _uiState.update { currentState ->
            currentState.copy(nombreProyecto = nombreProyecto, descripcion = descripcion, estado = estado, contacto = contacto)
        }
    }

    fun onChangeEliminar(id: String) {
        _uiState.update { currentState ->
            currentState.copy(ID = id)
        }
    }

    fun onChangeModificar(id: String, nombreProyecto: String, descripcion: String, estado: String, contacto: String) {
        _uiState.update { currentState ->
            currentState.copy(ID = id, nombreProyecto = nombreProyecto, descripcion = descripcion, estado = estado, contacto = contacto)
        }
    }

    suspend fun leerProyectos(): List<Proyecto> {
        return try {
            val proyectos = Firebase.firestore.collection("proyectos").get().await()
            val allProyectos = proyectos.toObjects<Proyecto>()

            // Filtra los proyectos si el usuario ha iniciado sesión
            if (loginViewModel.isLoggedIn.value) {
                val userEmail = loginViewModel.uiState.value.user
                return allProyectos.filter { it.contacto == userEmail }
            }

            allProyectos
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
    }

    fun agregarProyecto(proyecto: Proyecto) {
        db.collection("proyectos")
            .add(proyecto)
            .addOnSuccessListener { documentReference ->
                // Guarda el ID del documento
                val id = documentReference.id
                proyecto.id = id

                // Actualiza el proyecto con el nuevo ID
                db.collection("proyectos").document(id).set(proyecto)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun borrarProyecto(id: String) {
        db.collection("proyectos")
            .document(id)
            .delete()
            .addOnSuccessListener {
                // Puedes realizar acciones adicionales después de borrar un proyecto (opcional)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    fun modificarProyecto(id: String, proyecto: Proyecto) {
        db.collection("proyectos")
            .document(id)
            .set(proyecto)
            .addOnSuccessListener {
                // Puedes realizar acciones adicionales después de modificar un proyecto (opcional)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}
