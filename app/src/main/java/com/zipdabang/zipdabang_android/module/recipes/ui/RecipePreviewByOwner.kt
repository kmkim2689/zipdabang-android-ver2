package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCardLoading
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipePreviewState
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.GroupHeader

@Composable
fun RecipePreviewByOwner(
    recipeStateList: List<RecipePreviewState>,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    val mainViewModel = hiltViewModel<RecipeMainViewModel>()

    GroupHeader(
        groupName = OwnerType.ALL.type,
        formerHeaderStrawberry = OwnerType.ALL.title,
        latterHeaderChoco ="의 레시피",
        onClick = onOwnerTypeClick
    )

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (recipeStateList[0].isLoading) {
            items(5) {
                RecipeCardLoading()
            }
        } else {
            recipeStateList[0].data?.let { recipeItem ->
                items(recipeItem) {
                    RecipeCard(
                        recipeId = it.recipeId,
                        title = it.recipeName,
                        user = it.nickname,
                        thumbnail = it.thumbnailUrl,
                        date = it.createdAt,
                        likes = it.likes,
                        comments = it.comments ?: 0,
                        isLikeSelected = it.isLiked,
                        isScrapSelected = it.isScrapped,
                        onLikeClick = { recipeId ->
                            mainViewModel.toggleLike(recipeId)
                            mainViewModel.toggleLikeResult.value.isSuccessful == true
                        },
                        onScrapClick = { recipeId ->
                            mainViewModel.toggleScrap(recipeId)
                            mainViewModel.toggleScrapResult.value.isSuccessful == true
                        },
                        onItemClick = onRecipeClick
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

    }

    Spacer(modifier = Modifier.height(10.dp))

    GroupHeader(
        groupName = OwnerType.INFLUENCER.type,
        formerHeaderStrawberry = OwnerType.INFLUENCER.title,
        latterHeaderChoco ="의 레시피",
        onClick = onOwnerTypeClick
    )

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (recipeStateList[1].isLoading) {
            items(5) {
                RecipeCardLoading()
            }
        } else {
            recipeStateList[1].data?.let { recipeItem ->
                items(recipeItem) {
                    RecipeCard(
                        recipeId = it.recipeId,
                        title = it.recipeName,
                        user = it.nickname,
                        thumbnail = it.thumbnailUrl,
                        date = it.createdAt,
                        likes = it.likes,
                        comments = it.comments ?: 0,
                        isLikeSelected = it.isLiked,
                        isScrapSelected = it.isScrapped,
                        onLikeClick = { recipeId ->
                            mainViewModel.toggleLike(recipeId)
                            mainViewModel.toggleLikeResult.value.isSuccessful == true
                        },
                        onScrapClick = { recipeId ->
                            mainViewModel.toggleScrap(recipeId)
                            mainViewModel.toggleScrapResult.value.isSuccessful == true
                        },
                        onItemClick = onRecipeClick
                    )
                }

                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

    }

    Spacer(modifier = Modifier.height(10.dp))

    GroupHeader(
        groupName = OwnerType.USER.type,
        formerHeaderStrawberry = OwnerType.USER.title,
        latterHeaderChoco ="의 레시피",
        onClick = onOwnerTypeClick
    )

    Spacer(modifier = Modifier.height(6.dp))


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (recipeStateList[2].isLoading) {
            items(5) {
                RecipeCardLoading()
            }
        } else {
            recipeStateList[2].data?.let { recipeItem ->
                items(recipeItem) {
                    RecipeCard(
                        recipeId = it.recipeId,
                        title = it.recipeName,
                        user = it.nickname,
                        thumbnail = it.thumbnailUrl,
                        date = it.createdAt,
                        likes = it.likes,
                        comments = it.comments ?: 0,
                        isLikeSelected = it.isLiked,
                        isScrapSelected = it.isScrapped,
                        onLikeClick = { recipeId ->
                            mainViewModel.toggleLike(recipeId)
                            mainViewModel.toggleLikeResult.value.isSuccessful == true
                        },
                        onScrapClick = { recipeId ->
                            mainViewModel.toggleScrap(recipeId)
                            mainViewModel.toggleScrapResult.value.isSuccessful == true
                        },
                        onItemClick = onRecipeClick
                    )
                }

                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

    }
}