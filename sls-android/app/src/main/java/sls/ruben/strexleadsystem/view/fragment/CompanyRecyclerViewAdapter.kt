package sls.ruben.strexleadsystem.view.fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_lead.view.*
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.view.fragment.CompanyFragment.OnListFragmentInteractionListener

class CompanyRecyclerViewAdapter(
        private var mValues: MutableList<CompanyModel>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<CompanyRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CompanyModel
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_lead, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mValues.isEmpty()) return
        val item = mValues[position]
        //TODO: Add mode fields to view
        holder.mIdView.text = item.name
        holder.mContentView.text = item.address

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun updateItems(companies: MutableList<CompanyModel>) {
        mValues = companies
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        mValues.removeAt(position)
        notifyItemChanged(position)
    }

    fun getItem(position: Int): CompanyModel {
        return mValues[position]
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.lead_name
        val mContentView: TextView = mView.lead_company

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
