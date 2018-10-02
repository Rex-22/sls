package sls.ruben.strexleadsystem.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.repository.MasterRepository
import sls.ruben.strexleadsystem.util.Error
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners

class CompanyViewModel : ViewModel(), OnConnectionTimeoutListeners {

    private val masterRepository = MasterRepository()

    private val _companyModel = MutableLiveData<List<CompanyModel>>()
    val companyModel: LiveData<List<CompanyModel>>
        get() = _companyModel

    init {
        masterRepository.addTimeoutListener(this)
    }

    @SuppressLint("CheckResult")
    fun getCompanies() {
        masterRepository.getCompanies()
                .subscribeOn(Schedulers.newThread())
                .subscribe { response ->
                    if (response.isSuccessful) {
                        val model = response.body()!!
                        model[0].errorCode = Error.NONE
                        _companyModel.postValue(model)
                    } else {
                        //TODO: Error handling
                    }
                }
    }

    override fun onConnectionTimeout() {
        _companyModel.postValue(listOf(CompanyModel(errorCode = Error.TIME_OUT)))
    }
}