package com.diemoon.findapartykmm.data.local

import com.diemoon.findapartykmm.database.PartyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(PartyDatabase.Schema, "note.db")
    }
}