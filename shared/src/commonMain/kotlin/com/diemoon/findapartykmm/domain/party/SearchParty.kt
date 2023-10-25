package com.diemoon.findapartykmm.domain.party

import com.diemoon.findapartykmm.domain.time.DateTimeUtil

class SearchParty {
    fun execute(parties: List<Party>, query: String): List<Party>{
        if(query.isBlank()){
            return parties
        }
        return parties.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}