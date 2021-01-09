package com.pradeep.blueroseassignmentapp.apis

import com.pradeep.blueroseassignmentapp.models.Facts
import retrofit2.http.GET

interface FactApi {

    @GET("facts.json")
    suspend fun getFacts(): Facts
}