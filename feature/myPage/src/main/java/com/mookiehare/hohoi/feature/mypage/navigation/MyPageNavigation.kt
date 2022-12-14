package com.mookiehare.hohoi.feature.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mookiehare.hohoi.feature.mypage.MyPageRoute

const val myPageNavigationRoute = "my_page_route"

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    this.navigate(myPageNavigationRoute, navOptions)
}

fun NavGraphBuilder.myPageScreen() {
    composable(route = myPageNavigationRoute) {
        MyPageRoute()
    }
}