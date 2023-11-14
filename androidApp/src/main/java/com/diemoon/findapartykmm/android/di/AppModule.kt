package com.diemoon.findapartykmm.android.di

import android.app.Application
import com.diemoon.findapartykmm.data.local.DatabaseDriverFactory
import com.diemoon.findapartykmm.data.party.SqlDelightPartyDataSource
import com.diemoon.findapartykmm.database.PartyDatabase
import com.diemoon.findapartykmm.domain.party.PartyDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): PartyDataSource {
        return SqlDelightPartyDataSource(PartyDatabase(driver))
    }
}