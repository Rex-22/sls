package sls.ruben.strexleadsystem.api

import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.model.StaffModel

interface MasterAPIService {

    @POST("staff/authenticate")
    fun authStaff(
            @Body data: StaffModel
    ): Flowable<Response<StaffModel>>

    @GET("leads")
    fun getLeads(
            @Header("Authorization") token: String
    ): Flowable<Response<List<LeadModel>>>

    @GET("companies")
    fun getCompanies(
            @Header("Authorization") token: String
    ): Flowable<Response<List<CompanyModel>>>

}