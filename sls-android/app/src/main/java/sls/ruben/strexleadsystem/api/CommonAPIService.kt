package sls.ruben.strexleadsystem.api

import io.reactivex.Flowable
import okhttp3.Response
import retrofit2.http.GET

interface CommonAPIService {

    @GET("ping")
    fun ping(): Flowable<Response>

}
