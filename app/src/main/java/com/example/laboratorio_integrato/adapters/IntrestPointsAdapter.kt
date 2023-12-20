package com.example.laboratorio_integrato.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentManager
import com.example.laboratorio_integrato.R
import androidx.recyclerview.widget.RecyclerView
import com.example.laboratorio_integrato.ActivityViewModel
import com.example.laboratorio_integrato.fragments.InfoFragment
import com.example.laboratorio_integrato.model.IntrestPoint

import kotlinx.coroutines.Job

class IntrestPointsAdapter(private val dataList : List<IntrestPoint>,
   private val fragmentManager: FragmentManager, private val viewModel: ActivityViewModel) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.intrest_point_element, parent, false)
        return CustomViewHolder(view)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val dataItem = dataList[position]
        holder.label.text = dataItem.label
        dataItem.adjustMargins(holder, dataItem, dataList)

        holder.infoButton.setOnClickListener {
            viewModel.navBarShouldHide.value = true
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, InfoFragment.newInstance(position))
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}
class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Dichiarazione delle view all'interno dell'elemento della lista
    // Esempio: val textView: TextView = itemView.findViewById(R.id.textView)
    val label = itemView.findViewById<TextView>(R.id.label)
    val infoButton = itemView.findViewById<TextView>(R.id.infoButton)
    val holder = itemView.findViewById<CardView>(R.id.holder)
}