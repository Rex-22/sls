package sls.ruben.strexleadsystem.repository

import io.reactivex.Flowable
import retrofit2.Response
import sls.ruben.strexleadsystem.api.ApiService
import sls.ruben.strexleadsystem.api.MasterAPIService
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.model.StaffModel
import sls.ruben.strexleadsystem.prefService
import tech.bitcube.sabu.network.OnConnectionTimeoutListeners

class MasterRepository {

    private val api = ApiService.api!!.create(MasterAPIService::class.java)!!

    private val TOKEN
        get() = "bearer ${prefService.authKey!!}"

    fun login(username: String, password: String): Flowable<Response<StaffModel>>{
        val staff = StaffModel(
                username = username,
                email = username,
                password =  password
        )
        return api.authStaff(staff)
    }

    fun addTimeoutListener(listener: OnConnectionTimeoutListeners) {
        ApiService.add(listener)
    }

    fun getLeads(): Flowable<Response<List<LeadModel>>> {
        return api.getLeads(TOKEN)
    }

    fun getCompanies(): Flowable<Response<List<CompanyModel>>> {
        return api.getCompanies(TOKEN)
    }

}