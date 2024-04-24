package com.example.twit.data.mappers.di

import com.example.twit.data.mappers.RemoteItem2ToItemMapper
import com.example.twit.data.mappers.TemplateMapper
import com.example.twit.data.network.response.AnimeResponse
import com.example.twit.model.AnimeItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MappersModule {
    @Binds
    abstract fun bindsRemoteItem2ItemMapper(
        remoteItem2ToItemMapper: RemoteItem2ToItemMapper
    ): TemplateMapper<AnimeResponse, AnimeItem>




}