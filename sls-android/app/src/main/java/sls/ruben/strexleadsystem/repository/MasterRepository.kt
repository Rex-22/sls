package sls.ruben.strexleadsystem.repository

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import sls.ruben.strexleadsystem.App
import sls.ruben.strexleadsystem.api.ApiService
import sls.ruben.strexleadsystem.api.MasterAPIService
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.model.StaffModel
import sls.ruben.strexleadsystem.prefService
import sls.ruben.strexleadsystem.api.OnConnectionTimeoutListeners
import sls.ruben.strexleadsystem.database

class MasterRepository {

    private val api = ApiService.api!!.create(MasterAPIService::class.java)!!
    val database = App.database!!

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

    fun updateLead(lead: LeadModel): Flowable<Response<ResponseBody>> {
        return api.updateLead(TOKEN, lead.id, lead)
    }

    fun addLead(lead: LeadModel): Flowable<Response<ResponseBody>> {
        return api.addLead(TOKEN, lead)
    }

    fun removeLead(id: Int?): Flowable<Response<okhttp3.ResponseBody>> {
        return api.removeLead(TOKEN, id)
    }

    fun getCompanies(): Flowable<Response<List<CompanyModel>>> {
        return api.getCompanies(TOKEN)
    }

    fun updateCompany(company: CompanyModel): Flowable<Response<ResponseBody>> {
        return api.updateCompany(TOKEN, company.id, company)
    }

    fun addCompany(company: CompanyModel): Flowable<Response<ResponseBody>> {
        return api.addCompany(TOKEN, company)
    }


    fun removeCompany(id: Int?): Flowable<Response<okhttp3.ResponseBody>> {
        return api.removeCompany(TOKEN, id)
    }
}