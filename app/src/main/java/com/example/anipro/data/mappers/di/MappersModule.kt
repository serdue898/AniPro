package com.example.anipro.data.mappers.di

import com.example.anipro.data.database.entities.AnimeEntity
import com.example.anipro.data.mappers.ItemMapper2ToLocalItem
import com.example.anipro.data.mappers.LocalItem2ToItemMapper
import com.example.anipro.data.mappers.RemoteItem2ToItemMapper
import com.example.anipro.data.mappers.TemplateMapper
import com.example.anipro.data.network.response.Node
import com.example.anipro.model.AnimeData
import com.example.anipro.model.AnimeItem
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
    ): TemplateMapper<Node, AnimeItem>

    @Binds
    abstract fun bindsLocalItem2ItemMapper(
        localItem2ToItemMapper: LocalItem2ToItemMapper
    ): TemplateMapper<AnimeEntity, AnimeData>

    @Binds
    abstract fun bindsItemMapper2ToLocalItem(
        itemMapper2ToLocalItem: ItemMapper2ToLocalItem
    ): TemplateMapper<AnimeData, AnimeEntity>


}