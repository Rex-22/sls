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

    @POST("leads")
    fun addLead(
            @Header("Authorization")token: String,
            @Body lead: LeadModel): Flowable<Response<ResponseBody>>

    @PUT("leads/{id}")
    fun updateLead(
            @Header("Authorization")token: String,
            @Path("id")id: Int?,
            @Body lead: LeadModel): Flowable<Response<ResponseBody>>

    @DELETE("leads/{id}")
    fun removeLead(
            @Header("Authorization") token: String,
            @Path("id") id: Int?
    ): Flowable<Response<okhttp3.ResponseBody>>

    @GET("companies")
    fun getCompanies(
            @Header("Authorization") token: String
    ): Flowable<Response<List<CompanyModel>>>

    @POST("companies")
    fun addCompany(
            @Header("Authorization")token: String,
            @Body company: CompanyModel): Flowable<Response<ResponseBody>>


    @PUT("companies/{id}")
    fun updateCompany(
            @Header("Authorization")token: String,
            @Path("id")id: Int?,
            @Body company: CompanyModel): Flowable<Response<ResponseBody>>

    @DELETE("companies/{id}")
    fun removeCompany(
            @Header("Authorization") token: String,
            @Path("id") id: Int?
    ): Flowable<Response<ResponseBody>>
}