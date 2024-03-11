package com.dam2jms.factoriaproyectosfp24retrofit.screens

import com.google.firebase.firestore.PropertyName

data class Proyecto(
    @get:PropertyName("proyecto") @set:PropertyName("proyecto") var proyecto: String = "",
    @get:PropertyName("centro") @set:PropertyName("centro") var centro: String = "",
    @get:PropertyName("responsable") @set:PropertyName("responsable") var responsable: String = ""
)