package com.alexapps.formulario.ui.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alexapps.formulario.R
import com.alexapps.formulario.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val vacio = ""


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding // definimos una variable privada de solo la actividad principal

    private lateinit var viewModel: MainViewModel //creo Viewmodel

    private var calen = Calendar.getInstance() // Creamos una instancia de calendario, un objeto
    private var borndate = ""

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {//creo la actividad
        super.onCreate(savedInstanceState)

        //paso 2
        mainBinding = ActivityMainBinding.inflate(layoutInflater) //iniciamos la variable y me da acceso s los datos en la actividad
        val view = mainBinding.root //la variable view tomara lo que hay en vista de actividad
        setContentView(view)

        //paso 3, ViewModel

        //Parte del view mode
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // vinculo a la actividad Viewmode observadores de os live dat

        // variable que setea la infomacion seleccionada en el calendario
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calen.set(Calendar.YEAR, year)
            calen.set(Calendar.MONTH, month)
            calen.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val format = "dd/MM/yyyy" //defino un formato para el calendario
            val sdf = SimpleDateFormat(format) // cargo el formato al

            borndate = sdf.format(calen.time).toString()
            mainBinding.bornDateButton.text = borndate

        }

        // trabajaremos con los datos en la actividad principal, para eso es el with vitamos poner mainBinding en el resto
        with(mainBinding) {
            //Activacion del boton fechas llamo a un calendario en forma de dialogo
            bornDateButton.setOnClickListener {
                //Creamos un cuadro de dialogo, los que salen sobre la actividad
                DatePickerDialog(this@MainActivity,dateSetListener,
                    calen.get(Calendar.YEAR),
                    calen.get(Calendar.MONTH),
                    calen.get(Calendar.DAY_OF_MONTH)
                ).show()
                // Se pasa la fecha de hoy por defecto
            }

            //Activacion del boton registrar
            mainBinding.registrerButton.setOnClickListener {


                val name = mainBinding.nameEditText.text.toString() // si no se una el with se coloca asi
                //val lastName = lastNameEdit.text.toString()
                val email = editTextTextEmailAddress.text.toString()
                val contra = mainBinding.TextinputPassword.text.toString()
                val rcontra = mainBinding.TextInputRpaswword.text.toString()
                val gerder = if (mainBinding.MaleRadioButton.isChecked) getString(R.string.Female) else getString(R.string.Male)
                var hobbies = if (mainBinding.cookCheckBox.isChecked) getString(R.string.Cook) else vacio
                if (writeCheckBox.isChecked ) hobbies += getString(R.string.Write) + " "
                if (readCheckBox.isChecked ) hobbies += getString(R.string.Read) + " "
                if (sportCheckBox.isChecked ) hobbies += getString(R.string.Sport) + " "

                val bornCity = bornCitySpinner.selectedItem.toString()

                //condicional para registrar los datos, donde se valida que no esten en blanco los datos
                if (name.isNotEmpty() && email.isNotEmpty() && mainBinding.bornDateButton.text.toString().isNotEmpty() && contra.isNotEmpty()) {
                    if (mainBinding.cookCheckBox.isChecked || mainBinding.writeCheckBox.isChecked || mainBinding.readCheckBox.isChecked || mainBinding.sportCheckBox.isChecked) {
                        if (contra == rcontra) {

                            // lleno la platilla info
                            DataTextview.text = getString(R.string.info, name,email,gerder,hobbies,bornCity,borndate)

                            mainBinding.RContraTextInputLayout.error = null
                        } else {
                            mainBinding.RContraTextInputLayout.error = getString(R.string.msg_EPassword)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, getString(R.string.msg_Data), Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, getString(R.string.msg_Data), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}