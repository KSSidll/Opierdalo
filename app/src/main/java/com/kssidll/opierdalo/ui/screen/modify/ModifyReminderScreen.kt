package com.kssidll.opierdalo.ui.screen.modify

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kssidll.opierdalo.ExpandedPreviews
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme

@Composable
fun ModifyReminderScreen(
    uiState: ModifyReminderUiState,
    onEvent: (event: ModifyReminderEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            Text(uiState.name)
        }
    }
}

@PreviewLightDark
@ExpandedPreviews
@Composable
private fun ModifyReminderScreenPreview() {
    OpierdaloTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ModifyReminderScreen(
                uiState = ModifyReminderUiState(
                    name = "test"
                ),
                onEvent = {}
            )
        }
    }
}
