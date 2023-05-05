package com.example.utilityinsight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val entryList:ArrayList<Entries>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val accno: TextView = itemView.findViewById(R.id.d1_txt)
        val ldate: TextView = itemView.findViewById(R.id.d2_txt)
        val cdate: TextView = itemView.findViewById(R.id.d3_txt)
        val noofunits: TextView = itemView.findViewById(R.id.d4_txt)
        val total: TextView = itemView.findViewById(R.id.d5_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_usage_history,parent,false )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.accno.text = entryList[position].accountNumber
        holder.ldate.text = entryList[position].lastDate
        holder.cdate.text = entryList[position].currentDate
        holder.noofunits.text = entryList[position].units
        holder.total.text = entryList[position].totalAmount
    }
}