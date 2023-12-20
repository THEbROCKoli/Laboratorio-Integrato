package com.example.laboratorio_integrato.model

import android.view.ViewGroup
import androidx.core.view.marginEnd
import com.example.laboratorio_integrato.adapters.CustomViewHolder

class IntrestPoint(val label: String) {

    fun adjustMargins( holder : CustomViewHolder, dataItem: IntrestPoint, dataList: List<IntrestPoint>){
        if (dataItem == dataList.first()){
            val baseMargin = holder.holder.marginEnd
            val layoutParams = holder.holder.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginStart = 0
            layoutParams.marginEnd = baseMargin
            holder.holder.layoutParams = layoutParams
        } else if (dataItem == dataList.last()){
            val baseMargin = holder.holder.marginEnd
            val layoutParams = holder.holder.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginStart = baseMargin
            layoutParams.marginEnd = 0
            holder.holder.layoutParams = layoutParams
        }
    }

}