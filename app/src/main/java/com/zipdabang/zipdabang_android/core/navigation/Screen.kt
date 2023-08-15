package com.zipdabang.zipdabang_android.core.navigation

const val ROOT_ROUTE = "root"
const val SPLASH_ROUTE = "splash"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "main"
const val HOME_ROUTE = "main/home"
const val MARKET_ROUTE = "main/market"
const val BASKET_ROUTE = "main/basket"
const val RECIPES_ROUTE = "main/recipes"
const val MY_ROUTE = "main/my"
const val SHARED_ROUTE = "main/shared"

sealed class AuthScreen(val route: String) {
    object SignIn: AuthScreen(route = "auth/sign_in")
    object Terms: AuthScreen(route = "auth/sign_up/terms")
    object RegisterUserInfo: AuthScreen(route = "auth/sign_up/user_info")
    object RegisterNickname: AuthScreen(route = "auth/sign_up/nickname")
    object RegisterPreferences: AuthScreen(route = "auth/sign_up/preferences")
}

sealed class HomeScreen(val route : String){
    object Home : HomeScreen(route = "home/home")
    object Guide : HomeScreen(route = "home/guide")
}

sealed class MarektScreen(val route : String){
    object Home : HomeScreen(route = "market/home")
}
sealed class BasketScreen(val route : String){
    object Home : BasketScreen(route = "basket/home")
}
sealed class RecipeScreen(val route : String){
    object Home : RecipeScreen(route = "recipes/home")
}
sealed class MyScreen(val route : String){
    object Home : MyScreen(route = "my/home")
}


sealed class SharedScreen(val route : String){
    object DetailRecipe : SharedScreen(route = "shared/detail")
}