package com.plete.addrecordapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class MyAdapter(val context: Context, var dataList: ArrayList<CEOModel>?): RecyclerView.Adapter<MyAdapter.MyViewholder>() {
    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val llmain = view.llmain
        val tvId = view.tvId
        val tvName = view.tvName
        val tvCName = view.tvCName
        val ivEdit = view.Ivedit
        val ivDELETE = view.IvDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        return MyViewholder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val item = dataList?.get(position)

        holder.tvId.text = (position + 1).toString()
        holder.tvName.text = "(id: ${item?.id.toString()}) ${item?.name.toString()}"
        holder.tvCName.text = item?.company_name.toString()

        if (position % 2 == 0) {
            holder.llmain.setBackgroundColor(ContextCompat.getColor(context, R.color.lightBlue))
        } else {
            holder.llmain.setBackgroundColor(ContextCompat.getColor(context, R.color.pink))
        }

        holder.ivEdit.setOnClickListener {
            if (context is MainActivity) {
               context.updateRecordDialog(item!!)
            }
        }
        holder.ivDELETE.setOnClickListener {
            if (context is MainActivity) {
                context.deleteRecodDialog(item!!)
            }
        }
    }
}