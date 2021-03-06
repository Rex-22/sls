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
import kotlinx.android.synthetic.main.fragment_lead_edit.*
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.prefService
import sls.ruben.strexleadsystem.util.Error
import sls.ruben.strexleadsystem.viewModel.LeadViewModel

class LeadEditFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    lateinit var leadViewModel: LeadViewModel
    private lateinit var lead: LeadModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)

        return inflater.inflate(R.layout.fragment_lead_edit, container, false)
    }

    private fun onSave(view: View) {
        val lead = LeadModel(
                id = lead.id,
                firstname = edt_lead_name.text.toString(),
                lastname = edt_lead_surname.text.toString(),
                email = edt_email.text.toString(),
                cell = edt_cell.text.toString(),
                tell = edt_tell.text.toString(),
                companyId = edt_company.text.toString(),
                staffId = prefService.id,
                errorCode = Error.NONE
        )

        leadViewModel.updateLead(lead) {
            Snackbar.make(view, "Lead Saved!", Snackbar.LENGTH_LONG).show()
            this.fragmentManager!!.popBackStack()
        }

        listener?.onFragmentInteraction(lead)
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

    fun setLead(item: LeadModel?) {
        lead = item!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edt_lead_name.text?.clearAndAppend(lead.firstname!!)
        edt_lead_surname.text?.clearAndAppend(lead.lastname!!)
        edt_cell.text?.clearAndAppend(lead.cell!!)
        edt_tell.text?.clearAndAppend(lead.tell!!)
        edt_email.text?.clearAndAppend(lead.email!!)
        edt_company.text?.clearAndAppend(lead.companyId!!)
        btnSave.setOnClickListener { onSave(it) }
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(leadInfo: LeadModel)
    }
}

private fun Editable.clearAndAppend(text: String) {
    clear()
    append(text)
}
