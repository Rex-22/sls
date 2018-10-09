package sls.ruben.strexleadsystem.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import sls.ruben.strexleadsystem.R
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.util.Error
import sls.ruben.strexleadsystem.viewModel.LeadViewModel

class LeadEditFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    lateinit var leadViewModel: LeadViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        leadViewModel = ViewModelProviders.of(this).get(LeadViewModel::class.java)
        return inflater.inflate(R.layout.fragment_lead_edit, container, false)
    }

    fun onButtonPressed(view: View) {
        val lead = LeadModel(
                firstname = "",
                lastname = "",
                email = "",
                cell = "",
                tell = "",
                companyId = "",
                staffId = "",
                errorCode = Error.NONE
        )

//
//        leadViewModel.updateLead(lead, {
////            if(it.isSuccessful)
//                // TODO: Stop loding
//        })

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

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(leadInfo: LeadModel)
    }
}
