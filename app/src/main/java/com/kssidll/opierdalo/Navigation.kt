package com.kssidll.opierdalo

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kssidll.opierdalo.ui.screen.dashboard.DashboardRoute
import com.kssidll.opierdalo.ui.screen.modify.add.AddReminderRoute
import com.kssidll.opierdalo.ui.screen.modify.edit.EditReminderRoute
import com.kssidll.opierdalo.ui.screen.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Immutable
sealed class NavigationDestinations {
    @Serializable
    data object Dashboard: NavigationDestinations()

    @Serializable
    data object Settings: NavigationDestinations()

    @Serializable
    data object AddReminder: NavigationDestinations()

    @Serializable
    data class EditReminder(val reminderId: Long): NavigationDestinations()
}

val defaultNavigateEasing = CubicBezierEasing(
    0.48f,
    0.19f,
    0.05f,
    1.03f
)

const val defaultNavigateDurationMilis = 300

fun defaultNavigateEnterTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(
            durationMillis = defaultNavigateDurationMilis,
            easing = defaultNavigateEasing
        ),
        initialOffsetX = { it }
    )
}

fun defaultNavigatePopEnterTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(
            durationMillis = defaultNavigateDurationMilis,
            easing = defaultNavigateEasing
        ),
        initialOffsetX = { -it }
    )
}

fun defaultNavigateExitTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(
            durationMillis = defaultNavigateDurationMilis,
            easing = defaultNavigateEasing
        ),
        targetOffsetX = { -it }
    )
}

fun defaultNavigatePopExitTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(
            durationMillis = defaultNavigateDurationMilis,
            easing = defaultNavigateEasing
        ),
        targetOffsetX = { it }
    )
}

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationDestinations.Dashboard,
        enterTransition = {
            defaultNavigateEnterTransition()
        },
        popEnterTransition = {
            defaultNavigatePopEnterTransition()
        },
        exitTransition = {
            defaultNavigateExitTransition()
        },
        popExitTransition = {
            defaultNavigatePopExitTransition()
        },
    ) {
        composable<NavigationDestinations.Dashboard> {
            DashboardRoute(
                navigateSettings = {
                    navController.navigate(NavigationDestinations.Settings)
                },
                navigateAddReminder = {
                    navController.navigate(NavigationDestinations.AddReminder)
                },
                navigateEditReminder = { reminderId ->
                    navController.navigate(NavigationDestinations.EditReminder(reminderId))
                }
            )
        }

        composable<NavigationDestinations.Settings> {
            SettingsRoute(
                navigateBack = navigateBack,
            )
        }

        composable<NavigationDestinations.AddReminder> {
            AddReminderRoute(
                navigateBack = navigateBack
            )
        }

        composable<NavigationDestinations.EditReminder> {
            EditReminderRoute(
                navigateBack = navigateBack
            )
        }
    }
}

