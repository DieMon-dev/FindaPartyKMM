package com.diemoon.findapartykmm.android.party_detail

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diemoon.findapartykmm.android.R
import com.diemoon.findapartykmm.android.party_list.PartyListScreen
import com.diemoon.findapartykmm.android.party_list.PartyListViewModel
import com.diemoon.findapartykmm.domain.party.Party
import com.diemoon.findapartykmm.domain.party.PartyDataSource
import com.diemoon.findapartykmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyDetailViewModel @Inject constructor(
    private val partyDataSource: PartyDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val partyTitle = savedStateHandle.getStateFlow("partyTitle", "")
    private val isPartyTitleFocused = savedStateHandle.getStateFlow("isPartyTitleFocused", false)
    private val partyContent = savedStateHandle.getStateFlow("partyContent", "")
    private val isPartyContentFocused = savedStateHandle.getStateFlow("isPartyContentFocused", false)
    private val partyColor = savedStateHandle.getStateFlow("partyColor", Party.generateRandomColor())

    val state = combine(
        partyTitle,
        isPartyTitleFocused,
        partyContent,
        isPartyContentFocused,
        partyColor
    ){ title, isTitleFocused, content, isContentFocused, color ->
        PartyDetailState(
            partyTitle = title,
            isPartyTitleHintVisible = title.isEmpty() && !isTitleFocused,
            partyContent = content,
            isPartyContentHintVisible = content.isEmpty() && !isContentFocused,
            partyColor = color
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PartyDetailState())

    private val _hasPartyBeenPublished = MutableStateFlow(false)
    val hasPartyBeenPublished = _hasPartyBeenPublished.asStateFlow()

    private var existingPartyId: Long? = null

    init{
        savedStateHandle.get<Long>("partyId")?.let {existingPartyId ->
            if(existingPartyId == -1L){
                return@let
            }
            this.existingPartyId = existingPartyId
            viewModelScope.launch{
                partyDataSource.getPartyById(existingPartyId)?.let{ party ->
                    savedStateHandle["partyTitle"] = party.title
                    savedStateHandle["partyContent"] = party.content
                    savedStateHandle["partyColor"] = party.colorHex
                }
            }

        }
    }
    fun onPartyTitleChanged(text:String){
        savedStateHandle["partyTitle"] = text
    }

    fun onPartyContentChanged(text:String){
        savedStateHandle["partyContent"] = text
    }

    fun onPartyTitleFocusChanged(isFocused: Boolean){
        savedStateHandle["isPartyTitleFocused"] = isFocused
    }

    fun onPartyContentFocusChange(isFocused: Boolean){
        savedStateHandle["isPartyContentFocused"] = isFocused
    }

    fun saveParty(){
        viewModelScope.launch {
            partyDataSource.createParty(
                Party(
                    id = existingPartyId,
                    title = partyTitle.value,
                    content = partyContent.value,
                    colorHex = partyColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasPartyBeenPublished.value = true
        }
    }
    
}