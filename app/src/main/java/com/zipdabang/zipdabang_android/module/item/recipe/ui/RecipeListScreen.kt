package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSort
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarWithFullFunction
import com.zipdabang.zipdabang_android.ui.component.FloatingActionButton
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoryState: RecipeSubtitleState,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onLoginRequest: () -> Unit,
    showSnackbar: (String) -> Unit
) {

    val TAG = "RecipeListScreen"

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val viewModel = hiltViewModel<RecipeListViewModel>()

    val currentPlatformState = viewModel.currentPlatform.value

    val type: RecipeSubtitleState by remember {
        derivedStateOf {
            categoryState
        }
    }

    val sortList = listOf(
        RecipeSort.LATEST,
        RecipeSort.FOLLOW,
        RecipeSort.LIKES
    )

    val sortBy = viewModel.sortBy.value
    // val total = viewModel.total.value.toString()

    Log.i(TAG, "ownerType : $type")
    Log.i(TAG, "sortBy : $sortBy")
    // Log.i(TAG, "total : $total")


    val recipeList =
        if (categoryState.categoryId == -1 && categoryState.ownerType != null) {
            Log.d("RecipeList", "ownerType")
            viewModel.getOwnerItemCount(categoryState.ownerType)
            viewModel.getRecipeListByOwnerType(
                ownerType = categoryState.ownerType,
                orderBy = sortBy
            ).collectAsLazyPagingItems()
        } else {
            viewModel.getCategoryItemCount(categoryState.categoryId ?: 0)
            viewModel.getRecipeListByCategory(
                categoryId = categoryState.categoryId!!,
                orderBy = sortBy
            ).collectAsLazyPagingItems()
        }

    val lazyGridState = rememberLazyGridState()

    val isScrolled: Boolean by remember {
        derivedStateOf {
            // lazyList의 첫번째 아이템의 좌표가 0을 넘어서면 true
            lazyGridState.firstVisibleItemIndex > 0
        }
    }

    val likeState = viewModel.toggleLikeResult.collectAsState().value
    val scrapState = viewModel.toggleScrapResult.collectAsState().value

    if (likeState.errorMessage != null) {
        showSnackbar(likeState.errorMessage)
    }

    if (scrapState.errorMessage != null) {
        showSnackbar(scrapState.errorMessage)
    }

    var showLoginRequestDialog by remember {
        mutableStateOf(false)
    }


    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    AppBarWithFullFunction(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickStartIcon = {
                            onBackClick()
                        },
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = categoryState.let {
                            if (it.categoryId == -1 && it.ownerType != null) {
                                "레시피.zip"
                            } else if (it.categoryId != null && it.ownerType == null) {
                                when (it.categoryId) {
                                    0 -> "전체"
                                    1 -> "커피"
                                    2 -> "논카페인"
                                    3 -> "티"
                                    4 -> "에이드"
                                    5 -> "스무디"
                                    6 -> "과일 음료"
                                    7 -> "건강 음료"
                                    else -> ""
                                }
                            } else {
                                ""
                            }
                        }
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                floatingActionButton = {

                }
            ) { padding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                ) {

                    LoginRequestDialog(
                        showDialog = showLoginRequestDialog,
                        setShowDialog = { changedState ->
                            showLoginRequestDialog = changedState
                        }
                    ) {
                        onLoginRequest()
                    }

                    RecipeList(
                        modifier = Modifier,
                        onItemClick = onItemClick,
                        // total = total,
                        sortList = sortList,
                        onSortChange = { changedValue ->
                            when (changedValue) {
                                RecipeSort.LATEST.text -> {
                                    viewModel.setSortBy(RecipeSort.LATEST.type)
                                }
                                RecipeSort.LIKES.text -> {
                                    viewModel.setSortBy(RecipeSort.LIKES.type)
                                }
                                else -> {
                                    viewModel.setSortBy(RecipeSort.FOLLOW.type)
                                }
                            }

                        },
                        category = categoryState,
                        recipeList = recipeList,
                        likeState = likeState,
                        scrapState = scrapState,
                        onToggleLike = { recipeId, categoryId, ownerType ->
                            if (currentPlatformState == CurrentPlatform.TEMP
                                || currentPlatformState == CurrentPlatform.NONE) {
                                showLoginRequestDialog = true
                            } else {
                                viewModel.toggleLike(recipeId, categoryId, ownerType)
                            }
                        },
                        onToggleScrap = { recipeId, categoryId, ownerType ->
                            if (currentPlatformState == CurrentPlatform.TEMP
                                || currentPlatformState == CurrentPlatform.NONE) {
                                showLoginRequestDialog = true
                            } else {
                                viewModel.toggleScrap(recipeId, categoryId, ownerType)
                            }
                        },
                        lazyGridState = lazyGridState
                    ) {
                        categoryState.let {
                            if (it.categoryId == -1 && it.ownerType != null) {
                                when (it.ownerType) {
                                    OwnerType.OFFICIAL.type -> EveryoneSubtitle()
                                    OwnerType.BARISTA.type -> InfluencerSubtitle()
                                    OwnerType.USER.type -> OurSubtitle()
                                }
                            } else if (it.categoryId != null && it.ownerType == null) {
                                when (it.categoryId) {
                                    0 -> AllSubtitle()
                                    1 -> CoffeeSubtitle()
                                    2 -> NonCaffeineSubtitle()
                                    3 -> TeaSubtitle()
                                    4 -> AdeSubtitle()
                                    5 -> SmoothieSubtitle()
                                    6 -> FruitSubtitle()
                                    7 -> WellBeingSubtitle()
                                    else -> WellBeingSubtitle()
                                }
                            } else {
                                AllSubtitle()
                            }
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 40.dp, end = 16.dp),
                        isScrolled = isScrolled,
                        icon = R.drawable.zipdabanglogo_white,
                        title = "나의 레시피 공유하기"
                    ) {
                        if (currentPlatformState == CurrentPlatform.TEMP
                            || currentPlatformState == CurrentPlatform.NONE) {
                            showSnackbar("레시피를 작성하려면 로그인이 필요합니다.")
                        } else {
                            onShareClick()
                        }
                    }
                }
            }
        },
        drawerState = drawerState,
        navController = navController
    )

/*    val currentOnBack by rememberUpdatedState(onBackClick)

    // 뒤로가기 버튼을 눌렀을 때 callback을 실행
    val backCallback = remember {
        // Always intercept back events. See the SideEffect for
        // a more complete version
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBack()
                viewModel.deleteAllRecipes()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = true
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }*/
}