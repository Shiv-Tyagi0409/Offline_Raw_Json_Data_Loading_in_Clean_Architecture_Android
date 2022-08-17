package com.finance.loan.sepiapets.domain

import com.finance.loan.sepiapets.domain.model.Pets

interface MainRepository1 {

    fun getAllPets(): Pets
}