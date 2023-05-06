package com.example.utilityinsight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class WaterMyAdapter(
    private val entryList:ArrayList<Records>,
    private val conntext:Context,
    private val db:FirebaseFirestore,
    ) : RecyclerView.Adapter<WaterMyAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val waccname: TextView = itemView.findViewById(R.id.w_d1_txt)
        val waccno: TextView = itemView.findViewById(R.id.w_d2_txt)
        val wnumofdays: TextView = itemView.findViewById(R.id.w_d3_txt)
        val wnumofunits: TextView = itemView.findViewById(R.id.w_d4_txt)
        val wtotal: TextView = itemView.findViewById(R.id.w_d5_txt)
        val uid: TextView = itemView.findViewById(R.id.w_d6_txt)

        val updatebtn: Button = itemView.findViewById(R.id.update1)
        val deletebtn: Button = itemView.findViewById(R.id.delete1)
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
        holder.uid.text = entryList[position].userIDD

        holder.updatebtn.setOnClickListener {
            val intent = Intent(conntext, waterCalculateUpdate::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("accountname", entryList[position].accountname)
            intent.putExtra("accountnumber", entryList[position].accountnumber)
            intent.putExtra("numberofdays", entryList[position].numberofdays)
            intent.putExtra("numberofunits", entryList[position].numberofunits)
            intent.putExtra("totalamount", entryList[position].totalamount)
            intent.putExtra("uid", entryList[position].userIDD)
            conntext.startActivity(intent)
        }

        holder.deletebtn.setOnClickListener {
            entryList[position].userIDD?.let { it1 ->
                db.collection("wcalculate")
                    .document(it1)
                    .delete()
                    .addOnCompleteListener {
                        entryList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, entryList.size)
                        Toast.makeText(conntext, "Entry has been deleted!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }
}
