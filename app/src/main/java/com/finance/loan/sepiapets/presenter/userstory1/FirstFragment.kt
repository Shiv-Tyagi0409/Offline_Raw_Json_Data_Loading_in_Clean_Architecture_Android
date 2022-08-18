package com.finance.loan.sepiapets.presenter.userstory1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.loan.sepiapets.R
import com.finance.loan.sepiapets.databinding.FragmentFirstBinding
import com.finance.loan.sepiapets.domain.model.Pet
import com.finance.loan.sepiapets.common.extension.showGenericAlertDialog
import com.finance.loan.sepiapets.common.extension.showToast
import com.finance.loan.sepiapets.common.util.NetworkStatus
import com.finance.loan.sepiapets.common.util.NetworkStatusHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FirstViewModel by viewModels()
    private  val c = Calendar.getInstance()
    private var connectionStatus: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        observe()




    }


    private fun observe() {

        val day = c.get(Calendar.DAY_OF_WEEK)
        val hour = c.get(Calendar.HOUR_OF_DAY)

        if (hour in 9..18 ){
            if (day in 1..5){
                checkConnection()
                Log.d("ConnectionStatus",connectionStatus.toString())
                observeData()
            }else {
                requireActivity().showGenericAlertDialog("App function during business days only")
            }
        }else {
            requireActivity().showGenericAlertDialog("App function during working hours")
        }

    }

    private fun observeData() {

        setupRecyclerView()
        viewModel.mPets
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { petsList ->
                handleProducts(petsList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun handleProducts(petList: List<Pet>) {

        if (petList.isNotEmpty()){
            requireActivity().showToast("Data Loaded Successfully")
            binding.rvPetsList.adapter?.let {
                if(it is FirstAdapter){
                    it.updateList(petList)
                }
            }
        } else {
            context?.showToast("JsonParse Size 0")
        }



    }

    private fun setupRecyclerView() {

        val mAdapter = FirstAdapter(mutableListOf())
        mAdapter.setItemTapListener(object : FirstAdapter.OnItemTap{

            override fun onTap(pet: Pet) {


                if (connectionStatus){
                    val b = bundleOf("petUrl" to pet.content_url)
                    findNavController().navigate(R.id.SecondFragment, b)
                }else {
                    requireActivity().showToast("No Internet Please Try Again Later")
                }

            }
        })

        binding.rvPetsList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }


    }

    private fun checkConnection() {
        NetworkStatusHelper(requireContext()).observe(viewLifecycleOwner) {
            connectionStatus = it == NetworkStatus.Available
            if (it == NetworkStatus.Available){
                connectionStatus = true

            } else {
                connectionStatus = false
                requireActivity().showToast("No Internet Connectivity")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}