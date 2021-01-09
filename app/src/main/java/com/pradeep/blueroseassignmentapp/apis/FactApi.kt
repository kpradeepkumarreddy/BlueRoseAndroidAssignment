package com.pradeep.blueroseassignmentapp.apis

import com.pradeep.blueroseassignmentapp.roomdb.entities.Fact
import retrofit2.Call
import retrofit2.http.GET

interface FactApi {

    @GET("facts.json")
    fun getFacts(): Call<Fact>
}