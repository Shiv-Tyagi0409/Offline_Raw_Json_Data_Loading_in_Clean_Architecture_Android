package com.finance.loan.sepiapets.data.repository

import com.finance.loan.sepiapets.App
import com.finance.loan.sepiapets.domain.MainRepository1
import com.finance.loan.sepiapets.domain.model.Pets
import com.google.gson.Gson
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(app: App) : MainRepository1 {

    private val cntxt = app.applicationContext

    override fun getAllPets(): Pets {

        val jsonString = loadJSONFromAsset()

        return Gson().fromJson(jsonString, Pets::class.java)

    }

    private fun loadJSONFromAsset(): String {
        return try {
            cntxt.assets.open("pets_list.json").bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }
    }
}