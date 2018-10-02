package sls.ruben.strexleadsystem.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.repository.MasterRepository
import sls.ruben.strexleadsystem.util.Error
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners

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
                            _leadModel.postValue(mutableListOf())
                        }
                    } else {
                        //TODO: Error handling
                    }
                }
    }

    override fun onConnectionTimeout() {
        _leadModel.postValue(mutableListOf(LeadModel(errorCode = Error.TIME_OUT)))
    }

    @SuppressLint("CheckResult")
    fun removeLead(item: LeadModel) {
        masterRepository.removeLead(item.id)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
    }
}