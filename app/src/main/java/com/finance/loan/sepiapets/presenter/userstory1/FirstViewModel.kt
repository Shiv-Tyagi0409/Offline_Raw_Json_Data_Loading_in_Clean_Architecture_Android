package com.finance.loan.sepiapets.presenter.userstory1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.loan.sepiapets.domain.model.Pet
import com.finance.loan.sepiapets.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {

    private val pet = MutableStateFlow<List<Pet>>(mutableListOf())
    val mPets: StateFlow<List<Pet>> get() = pet

    fun getData(){

        viewModelScope.launch {

            val list = withContext(Dispatchers.IO) {
                useCase.getPetsList()
            }
            pet.value = list

            }
    }

}