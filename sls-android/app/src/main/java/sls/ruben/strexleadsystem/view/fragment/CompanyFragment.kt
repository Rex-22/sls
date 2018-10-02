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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.viewModel.CompanyViewModel

class CompanyFragment : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private var companies: List<CompanyModel> = listOf(CompanyModel())
    private lateinit var viewModel: CompanyViewModel

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
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = CompanyRecyclerViewAdapter(companies, listener)
        }
        view!!.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).setOnRefreshListener {
            viewModel.getCompanies()
        }
        return view
    }

    private fun refreshList(companies: List<CompanyModel>) {
        val listView = view!!.findViewById<RecyclerView>(R.id.list)
        (listView.adapter as CompanyRecyclerViewAdapter).updateItems(companies)
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
            viewModel.getCompanies()
            return true
        }
        return super.onContextItemSelected(item)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: CompanyModel?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                LeadFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
