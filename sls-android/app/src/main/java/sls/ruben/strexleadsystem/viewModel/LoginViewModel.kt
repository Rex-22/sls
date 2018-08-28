package sls.ruben.strexleadsystem.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import sls.ruben.strexleadsystem.model.StaffModel
import sls.ruben.strexleadsystem.prefService
import sls.ruben.strexleadsystem.repository.MasterRepository
import sls.ruben.strexleadsystem.util.Error
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners

class LoginViewModel: ViewModel(), OnConnectionTimeoutListeners {

    private val masterRepository = MasterRepository()

    private val _staffModel = MutableLiveData<StaffModel>()
    val staffModel: LiveData<StaffModel>
        get() = _staffModel

    init {
        masterRepository.addTimeoutListener(this)
    }

    @SuppressLint("CheckResult")
    fun login(username: String, password: String) {
        masterRepository.login(username, password)
                .subscribeOn(Schedulers.newThread())
                .subscribe { response ->
                    if (response.isSuccessful){
                        val model = response.body()!!
                        prefService.authKey = model.authToken
                        prefService.id = model.id.toString()
                        prefService.userStored = true
                        model.errorCode = Error.NONE
                        _staffModel.postValue(model)
                    }else {
                        if (response.code() == 403)
                            _staffModel.postValue(StaffModel(errorCode = Error.INVALID_PASSWORD))
                        if (response.code() == 404)
                            _staffModel.postValue(StaffModel(errorCode = Error.INVALID_EMAIL))
                    }
                }
    }

    fun logout() {
        prefService.userStored = false
        prefService.id = ""
        prefService.authKey = ""
    }

    override fun onConnectionTimeout() {
        _staffModel.postValue(StaffModel(errorCode = Error.TIME_OUT))
    }
}