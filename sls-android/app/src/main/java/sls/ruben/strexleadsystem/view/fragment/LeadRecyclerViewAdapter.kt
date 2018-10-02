package sls.ruben.strexleadsystem.view.fragment

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
        private var mValues: List<LeadModel>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<LeadRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LeadModel
            //TODO: Handel the click event for a lead cell being tapped
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_lead, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.firstname
        holder.mContentView.text = item.lastname

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateItems(items: List<LeadModel>) {
        mValues = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
