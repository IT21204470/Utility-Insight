package com.example.utilityinsight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GasAdapter(
    private val entryList:ArrayList<gasDetails>,
    private val conntext: Context,
    private val db: FirebaseFirestore,
    ) : RecyclerView.Adapter<GasAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val gaccno: TextView = itemView.findViewById(R.id.gas_display_accountNo)
        val gdate: TextView = itemView.findViewById(R.id.gas_display_datetext)
        val uid: TextView = itemView.findViewById(R.id.gas_display_id_text)
        val gunits: TextView = itemView.findViewById(R.id.gas_display_units_txt)

        val updatebtn: Button = itemView.findViewById(R.id.gas_entries_updte)
        val deletebtn: Button = itemView.findViewById(R.id.gas_entries_dlt)

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
        holder.uid.text = entryList[position].UID

        holder.updatebtn.setOnClickListener {
            val intent = Intent(conntext, gasUpdate::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("AccountNo", entryList[position].AccountNo)
            intent.putExtra("Date", entryList[position].Date)
            intent.putExtra("Units", entryList[position].Units)
            intent.putExtra("uid", entryList[position].UID)
            conntext.startActivity(intent)
        }

        holder.deletebtn.setOnClickListener {
            entryList[position].UID?.let { it1 ->
                db.collection("gascalculate")
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