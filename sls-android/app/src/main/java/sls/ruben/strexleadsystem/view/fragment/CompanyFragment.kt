package sls.ruben.strexleadsystem.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.util.SwipeToDeleteCallback
import sls.ruben.strexleadsystem.viewModel.CompanyViewModel

class CompanyFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private var companies: MutableList<CompanyModel> = mutableListOf()
    private lateinit var viewModel: CompanyViewModel
    private var shouldRefresh: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load companies from the server
        //TODO: Loading screen
        viewModel = ViewModelProviders.of(this).get(CompanyViewModel::class.java)
        viewModel.getCompanies()
        viewModel.companyModel.observe(this@CompanyFragment, Observer {
            companies = it!!
            this@CompanyFragment.refreshList(companies)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_company_list, container, false)
        // Set the adapter
        with(view.findViewById<RecyclerView>(R.id.list)) {
            adapter = CompanyRecyclerViewAdapter(companies, listener)
            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("Deleting a company will delete all leads related to that company")
                            .setPositiveButton("Yes") { _, _ ->
                                viewModel.removeCompany((adapter as CompanyRecyclerViewAdapter).getItem(viewHolder.adapterPosition))
                                (adapter as CompanyRecyclerViewAdapter).removeAt(viewHolder.adapterPosition)
                            }
                            .setNegativeButton("No") { dialogInterface, _ ->
                                refreshList(companies)
                                dialogInterface.dismiss()
                            }.show()
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
        view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).setOnRefreshListener {
            viewModel.getCompanies()
        }
        return view
    }

    private fun refreshList(companies: MutableList<CompanyModel>) {
        val listView = view!!.findViewById<RecyclerView>(R.id.list)
        (listView.adapter as CompanyRecyclerViewAdapter).updateItems(companies)
        view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing = false
    }

    override fun onStart() {
        super.onStart()

        if (shouldRefresh)
            refreshLeads()
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
            viewModel.getCompanies()
            return true
        }
        return super.onContextItemSelected(item)
    }

    fun queueRefreshCompany() {
        shouldRefresh = true
    }

    private fun refreshLeads() {
        this.view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing = true
        shouldRefresh = false
        viewModel.getCompanies()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: CompanyModel?)
    }
}
