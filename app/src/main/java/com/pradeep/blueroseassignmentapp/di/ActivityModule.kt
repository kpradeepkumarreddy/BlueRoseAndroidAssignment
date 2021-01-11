package com.pradeep.blueroseassignmentapp.di

import com.pradeep.blueroseassignmentapp.apis.FactApi
import com.pradeep.blueroseassignmentapp.repositories.FactRepository
import com.pradeep.blueroseassignmentapp.repositories.FactRepositoryImpl
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @ActivityScoped
    @Provides
    fun provideFactRepository(factRoomDB: FactRoomDB, factApi: FactApi): FactRepository =
        FactRepositoryImpl(factRoomDB, factApi)
}