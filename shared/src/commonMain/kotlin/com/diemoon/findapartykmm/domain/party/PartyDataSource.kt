package com.diemoon.findapartykmm.domain.party

interface PartyDataSource {
    suspend fun createParty(party: Party)
    suspend fun getPartyById(id: Long): Party?
    suspend fun getAllParties(): List<Party>
    suspend fun deletePartyById(id: Long)
}