package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.my.ui.FriendListScreen
import com.zipdabang.zipdabang_android.module.my.ui.LikeScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyrecipeScreen
import com.zipdabang.zipdabang_android.module.my.ui.ScrapScreen
import com.zipdabang.zipdabang_android.module.my.ui.ShoppingScreen
import com.zipdabang.zipdabang_android.module.my.ui.UserInfoScreen


fun NavGraphBuilder.MyNavGraph(navController: NavController) {

    navigation(startDestination = MyScreen.Home.route, route = MY_ROUTE) {
        composable(MyScreen.Home.route) {
            MyScreen(
                onClickBack = {
                    navController.navigate(MAIN_ROUTE){
                        launchSingleTop = true
                    }
                },
                onClickEdit = {

                },
                onClickLike = {
                    navController.navigate(MyScreen.Like.route)
                },
                onClickScrap = {
                    navController.navigate(MyScreen.Scrap.route)
                },
                onClickMyrecipe = {
                    navController.navigate(MyScreen.Myrecipe.route)
                },
                onClickShopping = {
                    navController.navigate(MyScreen.Shopping.route)
                },
                onClickFriendList = {
                    navController.navigate(MyScreen.FriendList.route)
                },
                onClickLogout = {
                    navController.navigate(MAIN_ROUTE){
                        launchSingleTop = true
                    }
                    Log.e("signup-tokens","로그아웃 클릭, onClick 실행 중")
                },
                onClickUserInfo = {
                    navController.navigate(MyScreen.UserInfo.route)
                }
            )
        }

        composable(MyScreen.Like.route) {
            LikeScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.Scrap.route) {
            ScrapScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.Myrecipe.route) {
            MyrecipeScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.Shopping.route) {
            ShoppingScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.FriendList.route) {
            FriendListScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                }
            )
        }
        composable(MyScreen.UserInfo.route) {
            UserInfoScreen(
                onClickBack = {
                    navController.popBackStack(MyScreen.Home.route, inclusive = false)
                },
                onClickEdit = {

                },
                onClickEditBasic = {},
                onClickEditDetail = {},
                onClickEditNickname = {},
                onClickLogout = {},
                onClickWithdraw = {}
            )
        }
    }
}




