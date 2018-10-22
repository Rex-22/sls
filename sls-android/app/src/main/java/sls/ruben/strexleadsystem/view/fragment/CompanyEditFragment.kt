package sls.ruben.strexleadsystem.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_company_edit.*
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.util.Error
import sls.ruben.strexleadsystem.viewModel.CompanyViewModel

class CompanyEditFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    lateinit var companyViewModel: CompanyViewModel
    private lateinit var company: CompanyModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        companyViewModel = ViewModelProviders.of(this).get(CompanyViewModel::class.java)

        return inflater.inflate(R.layout.fragment_company_edit, container, false)
    }

    private fun onSave(view: View) {
        val company = CompanyModel(
                id = company.id,
                name = edt_company_name.text.toString(),
                address = edt_company_address.text.toString(),
                tell = edt_tell.text.toString(),
                website= edt_website.text.toString(),
                errorCode = Error.NONE
        )

        companyViewModel.updateCompany(company) {
            Snackbar.make(view, "Company Saved!", Snackbar.LENGTH_LONG).show()
            this.fragmentManager!!.popBackStack()
        }

        listener?.onFragmentInteraction(company)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun setCompany(item: CompanyModel?) {
        company = item!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edt_company_name.text?.clearAndAppend(company.name!!)
        edt_company_address.text?.clearAndAppend(company.address!!)
        edt_tell.text?.clearAndAppend(company.tell!!)
        edt_website.text?.clearAndAppend(company.website!!)
        btnSave.setOnClickListener { onSave(it) }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(companyInfo: CompanyModel)
    }
}

private fun Editable.clearAndAppend(text: String) {
    clear()
    append(text)
}
