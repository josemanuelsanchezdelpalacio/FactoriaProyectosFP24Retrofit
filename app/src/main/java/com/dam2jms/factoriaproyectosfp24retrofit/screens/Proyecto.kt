package com.dam2jms.factoriaproyectosfp24retrofit.screens

import kotlinx.serialization.Serializable

@Serializable
data class Proyecto(
    val proyecto: String,
    val centro: String,
    val responsable: String
)