package com.diemoon.findapartykmm.data.party

import com.diemoon.findapartykmm.domain.party.Party
import database.PartyEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun PartyEntity.toParty(): Party {
    return Party(
        id = id,
        title = title,
        content = content,
        colorHex = colorHex,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}