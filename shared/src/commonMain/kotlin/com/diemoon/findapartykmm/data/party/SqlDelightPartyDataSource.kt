package com.diemoon.findapartykmm.data.party

import com.diemoon.findapartykmm.database.PartyDatabase
import com.diemoon.findapartykmm.domain.party.Party
import com.diemoon.findapartykmm.domain.party.PartyDataSource
import com.diemoon.findapartykmm.domain.time.DateTimeUtil

class SqlDelightPartyDataSource(db: PartyDatabase): PartyDataSource {

    private val queries = db.partyQueries
    override suspend fun createParty(party: Party) {
        queries.insertParty(
            id = party.id,
            title = party.title,
            content = party.content,
            colorHex = party.colorHex,
            created = DateTimeUtil.toEpochMillis(party.created)
        )
    }

    override suspend fun getPartyById(id: Long): Party? {
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

    override suspend fun deletePartyById(id: Long) {
        queries.deletePartyById(id)
    }
}