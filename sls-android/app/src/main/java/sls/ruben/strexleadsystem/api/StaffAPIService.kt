package sls.ruben.strexleadsystem.api

import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import sls.ruben.strexleadsystem.model.StaffModel

interface StaffAPIService {

    @POST("staff/authenticate")
    fun authStaff(
            @Body data: StaffModel
    ): Flowable<Response<StaffModel>>

}