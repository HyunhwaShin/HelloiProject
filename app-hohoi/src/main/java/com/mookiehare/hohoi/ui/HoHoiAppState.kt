package com.mookiehare.hohoi.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mookiehare.hohoi.feature.chat.navigation.chatNavigationRoute
import com.mookiehare.hohoi.feature.chat.navigation.navigateToChat
import com.mookiehare.hohoi.feature.mapmatching.navigation.mapMatchingNavigationRoute
import com.mookiehare.hohoi.feature.mapmatching.navigation.navigateToMapMatching
import com.mookiehare.hohoi.feature.mypage.navigation.myPageNavigationRoute
import com.mookiehare.hohoi.feature.mypage.navigation.navigateToMyPage
import com.mookiehare.hohoi.feature.randommatching.navigation.navigateToRandomMatching
import com.mookiehare.hohoi.feature.randommatching.navigation.randomMatchingNavigationRoute
import com.mookiehare.hohoi.navigation.TopLevelDestination
import com.mookiehare.hohoi.navigation.TopLevelDestination.*
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberHoHoiAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberAnimatedNavController()
): HoHoiAppState {
    return remember (navController, coroutineScope, windowSizeClass) {
        HoHoiAppState(navController, coroutineScope, windowSizeClass)
    }
}

class HoHoiAppState (
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            randomMatchingNavigationRoute -> RANDOM_MATCHING
            chatNavigationRoute -> CHAT
            mapMatchingNavigationRoute -> MAP_MATCHING
            myPageNavigationRoute -> MY_PAGE
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    /**
     * app ?????? Top Level Destination navigating UI Logic ?????????.
     * Top Level Destination ?????? ????????? ????????? back stack destination ????????? ?????????????????????.
     * navigate ??? ????????? ????????? save ?????? resotre ?????????.
     *
     * @param topLevelDestination: ????????? Destination.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // ???????????? item ??? ????????? ??? back stack ???
            // ??? ????????? ????????? ????????? ???????????? ?????? ???????????? ?????? ??????
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // ????????? item ??? ?????? ????????? ??? ????????? item ?????? ??????
            launchSingleTop = true
            // ????????? ????????? item ??? ?????? ????????? ??? ?????? ??????
            restoreState = true
        }

        when (topLevelDestination) {
            RANDOM_MATCHING -> navController.navigateToRandomMatching(topLevelNavOptions)
            CHAT -> navController.navigateToChat(topLevelNavOptions)
            MAP_MATCHING -> navController.navigateToMapMatching(topLevelNavOptions)
            MY_PAGE -> navController.navigateToMyPage(topLevelNavOptions)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}