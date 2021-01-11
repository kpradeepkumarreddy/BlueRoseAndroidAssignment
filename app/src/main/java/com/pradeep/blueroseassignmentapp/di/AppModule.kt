package com.pradeep.blueroseassignmentapp.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pradeep.blueroseassignmentapp.R
import com.pradeep.blueroseassignmentapp.roomdb.FactRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        FactRoomDB::class.java,
        context.resources.getString(R.string.fact_database)
    ).build()

    @Singleton
    @Provides
    fun provideFactDao(factDB: FactRoomDB) = factDB.factDao()

    @Singleton
    @Provides
    fun provideFactItemDao(factDB: FactRoomDB) = factDB.factItemDao()
}