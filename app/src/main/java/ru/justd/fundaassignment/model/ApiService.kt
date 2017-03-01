package ru.justd.fundaassignment.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Single

/**
 * Created by defuera on 01/02/2017.
 */
interface ApiService {

    @GET("feeds/Aanbod.svc/json/{key}")
    fun fetch(
            @Path("key") key: String,
            @Query("type") type: String,
            @Query("zo") searchQuery: String,
            @Query("page") page: Int,
            @Query("pageSize") pageSize: Int
    ): Single<ApiResponse<Makelaar>>
}