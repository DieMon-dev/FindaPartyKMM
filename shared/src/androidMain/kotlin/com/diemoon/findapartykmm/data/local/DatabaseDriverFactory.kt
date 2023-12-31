package com.diemoon.findapartykmm.data.local

import android.content.Context
import com.diemoon.findapartykmm.database.PartyDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver{
        return AndroidSqliteDriver(PartyDatabase.Schema, context, "party.db")
    }
}