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

    private val _leadModel = MutableLiveData<List<LeadModel>>()
    val leadModel: LiveData<List<LeadModel>>
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
                        model[0].errorCode = Error.NONE
                        _leadModel.postValue(model)
                    } else {
                        //TODO: Error handeling
                    }
                }
    }

    override fun onConnectionTimeout() {
        _leadModel.postValue(listOf(LeadModel(errorCode = Error.TIME_OUT)))
    }
}