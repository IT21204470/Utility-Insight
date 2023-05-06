package com.example.utilityinsight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val entryList:ArrayList<Entries>,
    private val conntext: Context
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val accno: TextView = itemView.findViewById(R.id.d1_txt)
        val ldate: TextView = itemView.findViewById(R.id.d2_txt)
        val cdate: TextView = itemView.findViewById(R.id.d3_txt)
        val noofunits: TextView = itemView.findViewById(R.id.d4_txt)
        val total: TextView = itemView.findViewById(R.id.d5_txt)
        val uID: TextView = itemView.findViewById(R.id.d6_txt)

        val delete: Button = itemView.findViewById(R.id.delete1)
        val update :Button = itemView.findViewById(R.id.update1)
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
        holder.uID.text = entryList[position].userID

        holder.update.setOnClickListener {
            val intent = Intent(conntext, updateCalculation::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("accNumber", entryList[position].accountNumber)
            intent.putExtra("lastDate", entryList[position].lastDate)
            intent.putExtra("cDate", entryList[position].currentDate)
            intent.putExtra("noOfUnits", entryList[position].units)
            intent.putExtra("total", entryList[position].totalAmount)
            intent.putExtra("uID", entryList[position].userID)
            conntext.startActivity(intent)

        }
    }
}