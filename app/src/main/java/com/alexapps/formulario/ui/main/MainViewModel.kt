package com.alexapps.formulario.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    //tipo de dato que puede ser observado por fuera de la actividad afuera
    private val Resultado: MutableLiveData<String> = MutableLiveData()
    val resultadoDone: LiveData<String> = Resultado

    //funciones para trabajar


}