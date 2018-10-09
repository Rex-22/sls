package sls.ruben.strexleadsystem.view.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_lead.view.*
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.view.fragment.LeadFragment.OnListFragmentInteractionListener

class LeadRecyclerViewAdapter(
        private var mValues: MutableList<LeadModel>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<LeadRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LeadModel
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_lead, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mValues.isEmpty()) return
        val item = mValues[position]
        holder.leadName.text = "${item.firstname} ${item.lastname}"
        holder.leadCompany.text = item.company.name

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateItems(items: MutableList<LeadModel>) {
        mValues = items
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        mValues.removeAt(position)
        notifyItemChanged(position)
    }

    fun getItem(position: Int): LeadModel {
        return mValues[position]
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val leadName: TextView = mView.lead_name
        val leadCompany: TextView = mView.lead_company
    }
}
