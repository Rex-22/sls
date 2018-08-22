package sls.ruben.strexleadsystem.repository

import io.reactivex.Flowable
import retrofit2.Response
import sls.ruben.strexleadsystem.api.ApiService
import sls.ruben.strexleadsystem.api.StaffAPIService
import sls.ruben.strexleadsystem.model.StaffModel
import sls.ruben.strexleadsystem.prefService
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners

class MasterRepository {

    private val api = ApiService.api!!.create(StaffAPIService::class.java)!!

    private val TOKEN
        get() = "bearer ${prefService.authKey!!}"

    fun login(username: String, password: String): Flowable<Response<StaffModel>>{
        val staff = StaffModel(
                id = 0,
                username = username,
                email = username,
                password =  password,
                authToken = ""
        )
        return api.authStaff(staff)
    }

    fun addTimeoutListener(listener: OnConnectionTimeoutListeners) {
        ApiService.add(listener)
    }

}