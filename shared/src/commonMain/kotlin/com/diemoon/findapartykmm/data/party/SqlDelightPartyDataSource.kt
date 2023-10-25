package com.diemoon.findapartykmm.data.party

import com.diemoon.findapartykmm.database.NoteDatabase
import com.diemoon.findapartykmm.domain.party.Party
import com.diemoon.findapartykmm.domain.party.PartyDataSource
import com.diemoon.findapartykmm.domain.time.DateTimeUtil

class SqlDelightPartyDataSource(db: NoteDatabase): PartyDataSource {

    private val queries = db.noteQueries
    override suspend fun createParty(party: Party) {
        queries.insertParty(
            id = party.id,
            title = party.title,
            content = party.content,
            colorHex = party.colorHex,
            created = DateTimeUtil.toEpochMillis(party.created)
        )
    }

    override suspend fun getNoteById(id: Long): Party? {
        return queries
            .getPartyById(id)
            .executeAsOneOrNull()
            ?.toParty()
    }

    override suspend fun getAllParties(): List<Party> {
        return queries
            .getAllParties()
            .executeAsList()
            .map{ it.toParty() }
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deletePartyById(id)
    }
}