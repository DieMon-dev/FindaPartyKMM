package com.diemoon.findapartykmm.android.party_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

@Composable
fun PartyDetailScreen(
    partyId: Long,
    navController: NavController,
    viewModel: PartyDetailViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    val hasPartyBeenPublished by viewModel.hasPartyBeenPublished.collectAsState()
    
    LaunchedEffect(key1 = hasPartyBeenPublished){
        if(hasPartyBeenPublished){
            navController.popBackStack()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveParty,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Add party",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.partyColor))
                .fillMaxSize()
                .padding(padding)
                .padding( 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .align(End)
            )
            TransparentHintTextField(
                text = state.partyTitle,
                hint = "Enter party title...",
                isHintVisible = state.isPartyTitleHintVisible,
                onValueChanged = viewModel::onPartyTitleChanged,
                onFocusChanged = {
                    viewModel.onPartyTitleFocusChanged(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.partyContent,
                hint = "Enter party information...",
                isHintVisible = state.isPartyContentHintVisible,
                onValueChanged = viewModel::onPartyContentChanged,
                onFocusChanged = {
                    viewModel.onPartyContentFocusChange(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}