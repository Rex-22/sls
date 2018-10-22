package sls.ruben.strexleadsystem.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.util.SwipeToDeleteCallback
import sls.ruben.strexleadsystem.viewModel.LeadViewModel

class LeadFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private var leads: MutableList<LeadModel> = mutableListOf()
    private lateinit var viewModel: LeadViewModel
    private var shouldRefresh: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the leads from the server
        //TODO: Loading screen
        viewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)
        viewModel.getLeads()
        viewModel.leadModel.observe(this@LeadFragment, Observer {
            leads = it
            this@LeadFragment.refreshList(leads)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lead_list, container, false)
        // Set the adapter
        with(view.findViewById<RecyclerView>(R.id.list)) {
            adapter = LeadRecyclerViewAdapter(leads, listener)

            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.removeLead((adapter as LeadRecyclerViewAdapter).getItem(viewHolder.adapterPosition))
                    (adapter as LeadRecyclerViewAdapter).removeAt(viewHolder.adapterPosition)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
        view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).setOnRefreshListener {
            viewModel.getLeads()
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        if (shouldRefresh)
            refreshLeads()
    }

    private fun refreshList(leads: MutableList<LeadModel>) {
        val listView = view!!.findViewById<RecyclerView>(R.id.list)
        (listView.adapter as LeadRecyclerViewAdapter?)!!.updateItems(leads)
        view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menu_refresh) {
            this.view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing = true
            viewModel.getLeads()
            return true
        }
        return super.onContextItemSelected(item)
    }

    fun queueRefreshLeads() {
        shouldRefresh = true
    }

    private fun refreshLeads() {
        this.view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing = true
        shouldRefresh = false
        viewModel.getLeads()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: LeadModel?)
    }
}
