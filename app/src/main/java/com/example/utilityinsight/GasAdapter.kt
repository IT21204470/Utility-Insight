package com.example.utilityinsight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GasAdapter(private val entryList:ArrayList<gasDetails>) : RecyclerView.Adapter<GasAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val gaccno: TextView = itemView.findViewById(R.id.gas_display_accountNo)
        val gdate: TextView = itemView.findViewById(R.id.gas_display_datetext)
        val gunits: TextView = itemView.findViewById(R.id.gas_display_unit_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gas_list_items,parent,false )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.gaccno.text = entryList[position].AccountNo
        holder.gdate.text = entryList[position].Date
        holder.gunits.text = entryList[position].Units

    }
}