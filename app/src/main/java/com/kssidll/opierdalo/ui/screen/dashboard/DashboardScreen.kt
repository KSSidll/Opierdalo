package com.kssidll.opierdalo.ui.screen.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kssidll.opierdalo.ExpandedPreviews
import com.kssidll.opierdalo.R
import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.data.toReminder
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (event: DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val nestedScrollConnection = scrollBehavior.nestedScrollConnection

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val bottomSheetScaffoldTextFocusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(bottomSheetScaffoldState.bottomSheetState.currentValue) {
        if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Hidden) {
            bottomSheetScaffoldTextFocusRequester.freeFocus()
            focusManager.clearFocus(true)
            softwareKeyboardController?.hide()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            TextField(
                value = uiState.newReminderName,
                onValueChange = {
                    onEvent(DashboardEvent.SetNewReminderName(it))
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                        softwareKeyboardController?.hide()
                        bottomSheetScaffoldTextFocusRequester.freeFocus()
                        focusManager.clearFocus(true)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .focusRequester(bottomSheetScaffoldTextFocusRequester)
            )
        },
        modifier = modifier
    ) { _ ->
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
                        bottomSheetScaffoldTextFocusRequester.requestFocus()
                        softwareKeyboardController?.show()
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            },
            contentWindowInsets = WindowInsets.navigationBars,
        ) { innerPaddingValues ->
            Column(
                modifier = Modifier
                    .padding(innerPaddingValues)
                    .consumeWindowInsets(innerPaddingValues)
                    .verticalScroll(rememberScrollState())
                    .nestedScroll(nestedScrollConnection)
                    .fillMaxSize()
            ) {

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
                        ReminderEntity(0, "test").toReminder(),
                        ReminderEntity(1, "test").toReminder(),
                        ReminderEntity(2, "test").toReminder(),
                    )
                ),
                onEvent = {}
            )
        }
    }
}
