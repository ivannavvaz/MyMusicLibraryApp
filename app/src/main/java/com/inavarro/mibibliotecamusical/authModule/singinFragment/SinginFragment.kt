package com.inavarro.mibibliotecamusical.authModule.singinFragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.authModule.AuthActivity
import com.inavarro.mibibliotecamusical.authModule.authFragment.AuthFragmentDirections
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.User
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfo
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserSingin
import com.inavarro.mibibliotecamusical.databinding.FragmentSinginBinding
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.services.SinginService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale





class SinginFragment : Fragment() {

    lateinit var mBinding: FragmentSinginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSinginBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCountries()

        mBinding.cbBack.setOnClickListener {
            findNavController().navigate(
                SinginFragmentDirections.actionSinginFragmentToAuthFragment()
            )
        }

        mBinding.etBirthDate.setOnClickListener {
            showDatePickerDialog()
        }

        mBinding.btnRegister.setOnClickListener {
            singin()
        }
    }

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            OnDateSetListener { _: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth1: Int ->
                val selectedDate =
                    dayOfMonth1.toString() + "/" + (monthOfYear + 1) + "/" + year1
                mBinding.etBirthDate.setText(selectedDate)
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun getCountries() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SinginService::class.java)

        lifecycleScope.launch {
            try {
                val response = service.getCountries()

                if (response.isSuccessful) {
                    val countries = response.body()
                    val countryNames = mutableListOf<String>()
                    countries?.forEach {
                        countryNames.add(it.flag  + " " + it.name.common )
                    }

                    val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, countryNames.toTypedArray())

                    (mBinding.actCountry as? MaterialAutoCompleteTextView)?.setAdapter(adapter)

                    Log.d("COUNTRIES", countryNames.toString())
                } else {
                    Toast.makeText(context, "Error al obtener los países", Toast.LENGTH_SHORT).show()
                    Log.e("COUNTRIES", response.errorBody().toString())
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Error al obtener los países", Toast.LENGTH_SHORT).show()
                Log.e("COUNTRIES", e.message.toString())
            }
        }

    }

    private fun singin() {
        val user = mBinding.etUser.text.toString().trim()

        if (user.isEmpty()) {
            Toast.makeText(context, "Por favor, cree su nombre de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        if (user.contains("@")) {
            Toast.makeText(context, "El nombre de usuario no puede contener '@'", Toast.LENGTH_SHORT).show()
            return
        }

        val email = mBinding.etEmail.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(context, "Por favor, rellene su email", Toast.LENGTH_SHORT).show()
            return
        }

        val password = mBinding.etPassword.text.toString().trim()

        if (password.isEmpty()) {
            Toast.makeText(context, "Por favor, escriba su contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val password2 = mBinding.etPasswordConfirm.text.toString().trim()

        if (password2.isEmpty()) {
            Toast.makeText(context, "Por favor, repita su contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        val postalCode = mBinding.etPostalCode.text.toString().trim()

        if (postalCode.isEmpty()) {
            Toast.makeText(context, "Por favor, rellene el código postal", Toast.LENGTH_SHORT).show()
            return
        }


        val countryFlag = mBinding.actCountry.text.toString()

        if (countryFlag.isEmpty()) {
            Toast.makeText(context, "Por favor, seleccione un país", Toast.LENGTH_SHORT).show()
            return
        }

        val country = countryFlag.split(" ")[1]

        val birthDate = mBinding.etBirthDate.text.toString().trim()

        if (birthDate.isEmpty()) {
            Toast.makeText(context, "Por favor, seleccione una fecha de nacimiento", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = when (mBinding.cgGender.checkedChipId) {
            R.id.chipHombre -> "M"
            R.id.chipMujer -> "F"
            else -> {
                Toast.makeText(context, "Por favor, seleccione un género", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (password != password2) {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        var usuario = UserSingin(
            user,
            password,
            email,
            gender,
            convertToISO8601(birthDate),
            country,
            postalCode
        )

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SinginService::class.java)

        lifecycleScope.launch {
            try {

                val validateResponse = service.validateUser(UserInfo(email, user))

                if (validateResponse.isSuccessful) {
                    Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (validateResponse.code() == 401) {

                    val response = service.singinUser(usuario)

                    if (response.isSuccessful) {
                        val finalUser = response.body()
                        Toast.makeText(context, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            SinginFragmentDirections.actionSinginFragmentToLoginFragment()
                        )
                    } else {
                        Toast.makeText(context, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                        Log.e("USER", response.errorBody().toString())
                    }

                }

            } catch (e: Exception) {
                Toast.makeText(context, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                Log.e("USER", e.message.toString())
            }
        }
    }

    fun convertToISO8601(dateString: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }



}