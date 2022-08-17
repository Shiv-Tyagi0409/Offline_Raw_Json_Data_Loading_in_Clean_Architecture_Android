package com.finance.loan.sepiapets.domain.usecase

import com.finance.loan.sepiapets.domain.model.Pet
import com.finance.loan.sepiapets.domain.MainRepository1
import javax.inject.Inject

class UseCase @Inject constructor(private val mainRepository: MainRepository1) {




    fun getPetsList(): List<Pet> {
        return mainRepository.getAllPets().pets
    }
}