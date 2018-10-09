package sls.ruben.strexleadsystem.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import sls.ruben.strexleadsystem.App
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.repository.MasterRepository
import sls.ruben.strexleadsystem.util.Error
import sls.ruben.strexleadsystem.api.OnConnectionTimeoutListeners

class LeadViewModel : ViewModel(), OnConnectionTimeoutListeners {

    private val masterRepository = MasterRepository()

    private val _leadModel = MutableLiveData<MutableList<LeadModel>>()
    val leadModel: LiveData<MutableList<LeadModel>>
        get() = _leadModel

    init {
        masterRepository.addTimeoutListener(this)
    }

    @SuppressLint("CheckResult")
    fun getLeads() {
        masterRepository.getLeads()
                .subscribeOn(Schedulers.newThread())
                .subscribe { response ->
                    if (response.isSuccessful) {
                        val model = response.body()!!
                        if (!model.isEmpty()) {
                            model[0].errorCode = Error.NONE
                            _leadModel.postValue(model.toMutableList())
                        } else {
                            masterRepository.database.leadDao().getAll().subscribe {
                                _leadModel.postValue(it.toMutableList())
                            }
                        }
                    } else {
                        //TODO: Error handling
                    }
                }
    }

    override fun onConnectionTimeout() {
        _leadModel.postValue(mutableListOf(LeadModel(errorCode = Error.TIME_OUT)))
    }

    fun addLead(lead: LeadModel) {
        masterRepository.addLead(lead)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
    }

    fun updateLead(lead: LeadModel, leadUpdateCallback: (Response<ResponseBody>) -> Unit = {}) {
        masterRepository.updateLead(lead)
                .subscribeOn(Schedulers.newThread())
                .subscribe(leadUpdateCallback)
    }

    @SuppressLint("CheckResult")
    fun removeLead(item: LeadModel, leadRemovedCallback: (Response<ResponseBody>) -> Unit = {}) {
        masterRepository.removeLead(item.id)
                .subscribeOn(Schedulers.newThread())
                .subscribe(leadRemovedCallback)
    }
}