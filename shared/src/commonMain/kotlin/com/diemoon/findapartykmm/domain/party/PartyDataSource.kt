package com.diemoon.findapartykmm.domain.party

interface PartyDataSource {
    suspend fun createParty(party: Party)
    suspend fun getNoteById(id: Long): Party?
    suspend fun getAllParties(): List<Party>
    suspend fun deleteNoteById(id: Long)
}