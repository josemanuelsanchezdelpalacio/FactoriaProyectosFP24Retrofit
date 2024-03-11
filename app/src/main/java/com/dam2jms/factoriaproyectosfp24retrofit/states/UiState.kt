package com.dam2jms.factoriaproyectosfp24retrofit.states

import com.dam2jms.factoriaproyectosfp24retrofit.screens.Proyecto
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

data class UiState(
    var user: String = "",
    var password: String = "",
    var repeat_password: String = "",
    val auth: FirebaseAuth = Firebase.auth,

    var proyecto: String = "",
    var centro: String = "",
    var responsable: String = "",
    var listaProyectos: List<Proyecto> = listOf()
)