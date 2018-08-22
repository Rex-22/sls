package sls.ruben.strexleadsystem.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import sls.ruben.strexleadsystem.model.StaffModel
import sls.ruben.strexleadsystem.repository.MasterRepository
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners

class LoginViewModel: ViewModel(), OnConnectionTimeoutListeners {

    private val masterRepository = MasterRepository()

    private val _staffModel = MutableLiveData<StaffModel>()
    val staffModel: LiveData<StaffModel>
        get() = _staffModel

    init {
        masterRepository.addTimeoutListener(this)
    }

    fun login(username: String, password: String) {
        masterRepository.login(username, password)
                .subscribeOn(Schedulers.newThread())
                .subscribe { respose ->
                    if (respose.isSuccessful){

                    }else {
                        _staffModel.postValue(null)
                        _staffModel.postValue()
                    }
                }
    }

    override fun onConnectionTimeout() {
        _staffModel.postValue(null)
        TODO("Implement error handling")
    }
}