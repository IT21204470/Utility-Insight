package com.example.utilityinsight

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WaterMyAdapter(private val entryList:ArrayList<Records>) : RecyclerView.Adapter<WaterMyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val waccname: TextView = itemView.findViewById(R.id.w_d1_txt)
        val waccno: TextView = itemView.findViewById(R.id.w_d2_txt)
        val wnumofdays: TextView = itemView.findViewById(R.id.w_d3_txt)
        val wnumofunits: TextView = itemView.findViewById(R.id.w_d4_txt)
        val wtotal: TextView = itemView.findViewById(R.id.w_d5_txt)
        val updatebtn: Button = itemView.findViewById(R.id.update1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.water_single_usage_page,parent,false )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.waccname.text = entryList[position].accountname
        holder.waccno.text = entryList[position].accountnumber
        holder.wnumofdays.text = entryList[position].numberofdays
        holder.wnumofunits.text = entryList[position].numberofunits
        holder.wtotal.text = entryList[position].totalamount

        holder.updatebtn.findViewById<Button>(R.id.update1).setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, waterCalculateUpdate::class.java)
            context.startActivity(intent)
        }
    }
}
