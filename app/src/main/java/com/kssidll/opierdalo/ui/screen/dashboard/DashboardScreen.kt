package com.kssidll.opierdalo.ui.screen.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kssidll.opierdalo.ExpandedPreviews
import com.kssidll.opierdalo.R
import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.data.identifier
import com.kssidll.opierdalo.domain.data.toReminder
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (event: DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val nestedScrollConnection = scrollBehavior.nestedScrollConnection

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                actions = {
                    // 'settings' action
                    IconButton(
                        onClick = {
                            onEvent(DashboardEvent.NavigateSettings)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(id = R.string.navigate_to_settings_description)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(DashboardEvent.NavigateAddNewReminder)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        contentWindowInsets = WindowInsets.navigationBars,
        modifier = modifier
    ) { innerPaddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPaddingValues)
                .consumeWindowInsets(innerPaddingValues)
                .nestedScroll(nestedScrollConnection)
                .fillMaxSize()
        ) {
            items(
                count = uiState.reminder.size,
                key = { uiState.reminder[it].identifier() }
            ) {
                val reminder = uiState.reminder[it]
                val interactionSource = remember { MutableInteractionSource() }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .combinedClickable(
                            interactionSource = interactionSource,
                            indication = LocalIndication.current,
                            onClick = {
                                onEvent(DashboardEvent.ToggleReminderCompleteStatus(reminder))
                            },
                            onLongClick = {
                                onEvent(DashboardEvent.NavigateEditReminder(reminder))
                            }
                        )
                ) {
                    Checkbox(
                        checked = reminder.complete,
                        onCheckedChange = { _ ->
                            onEvent(DashboardEvent.ToggleReminderCompleteStatus(reminder))
                        }
                    )
                    Text(
                        text = reminder.name
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@ExpandedPreviews
@Composable
private fun DashboardScreenPreview() {
    OpierdaloTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DashboardScreen(
                uiState = DashboardUiState(
                    reminder = persistentListOf(
                        ReminderEntity(0, "test0").toReminder(),
                        ReminderEntity(1, "test1", true).toReminder(),
                        ReminderEntity(2, "test2").toReminder(),
                    )
                ),
                onEvent = {}
            )
        }
    }
}
