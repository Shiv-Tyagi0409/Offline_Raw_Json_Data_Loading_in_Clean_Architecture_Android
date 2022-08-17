package com.finance.loan.sepiapets.presenter.userstory1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.finance.loan.sepiapets.databinding.PetListBinding
import com.finance.loan.sepiapets.domain.model.Pet

class FirstAdapter (private val PetsList: MutableList<Pet>) :
    RecyclerView.Adapter<FirstAdapter.ViewHolder>() {

    interface OnItemTap {
        fun onTap(pet: Pet)
    }

    fun setItemTapListener(l: OnItemTap){
        onTapListener = l
    }

    private var onTapListener: OnItemTap? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(mZone: List<Pet>){
        PetsList.clear()
        PetsList.addAll(mZone)
        notifyDataSetChanged()
    }

    inner class ViewHolder (private val itemBinding: PetListBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(pet: Pet) {

            itemBinding.petNameTextView.text = pet.title
            Glide.with(itemBinding.root.context).load(pet.image_url).into(itemBinding.ivPetImage)
            itemBinding.root.setOnClickListener {
                onTapListener?.onTap(pet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PetListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(PetsList[position])
    }

    override fun getItemCount() = PetsList.size
}