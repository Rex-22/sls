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

    private val _companyModel = MutableLiveData<MutableList<CompanyModel>>()
    val companyModel: LiveData<MutableList<CompanyModel>>
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
                        if (model.isNotEmpty()) {
                            model[0].errorCode = Error.NONE
                            _companyModel.postValue(model.toMutableList())
                        } else {
                            _companyModel.postValue(mutableListOf())
                        }
                    } else {
                        //TODO: Error handling
                    }
                }
    }


    override fun onConnectionTimeout() {
        _companyModel.postValue(mutableListOf(CompanyModel(errorCode = Error.TIME_OUT)))
    }

    @SuppressLint("CheckResult")
    fun removeCompany(item: CompanyModel) {
        masterRepository.removeCompany(item.id)
                .subscribeOn(Schedulers.newThread())
                .subscribe()
    }

}