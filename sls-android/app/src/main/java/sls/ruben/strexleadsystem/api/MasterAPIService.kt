package sls.ruben.strexleadsystem.api

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
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

    @DELETE("leads/{id}")
    fun removeLead(
            @Header("Authorization") token: String,
            @Path("id") id: Int?
    ): Flowable<Response<okhttp3.ResponseBody>>

    @GET("companies")
    fun getCompanies(
            @Header("Authorization") token: String
    ): Flowable<Response<List<CompanyModel>>>

    @DELETE("companies/{id}")
    fun removeCompany(
            @Header("Authorization") token: String,
            @Path("id") id: Int?
    ): Flowable<Response<ResponseBody>>

}