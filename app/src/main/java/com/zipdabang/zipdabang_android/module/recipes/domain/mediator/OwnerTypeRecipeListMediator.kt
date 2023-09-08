package com.zipdabang.zipdabang_android.module.recipes.domain.mediator

import androidx.datastore.core.DataStore
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeItemEntity
import kotlinx.coroutines.flow.first

class OwnerTypeRecipeListMediator(
    private val recipeApi: RecipeApi,
    private val database: Paging3Database,
    private val datastore: DataStore<Token>,
    private val ownerType: String,
    private val orderBy: String
): RecipeMediator<RecipeItemEntity>(recipeApi, database) {

    private val recipeListDao = database.recipeListDao()
    private val remoteKeyDao = database.RemoteKeyDao()

    override suspend fun getResponse(loadType: LoadType, currentPage: Int) {

        val accessToken = ("Bearer " + datastore.data.first().accessToken)

        val response = recipeApi.getRecipeListByOwnerType(
            accessToken = accessToken,
            ownerType = ownerType,
            order = orderBy,
            pageIndex = currentPage
        ).result?.recipeList?.map {
            it.toRecipeItemEntity()
        } ?: emptyList()

        val endOfPaginationReached = response.isEmpty()

        val prevPage = if (currentPage == 1) null else currentPage - 1
        val nextPage = if (endOfPaginationReached) null else currentPage + 1

        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                recipeListDao.deleteAllRecipes()
                remoteKeyDao.deleteRemoteKeys()
            }

            val keys = response.map { recipeItem ->
                RemoteKeys(
                    id = recipeItem.recipeId,
                    prevPage = prevPage,
                    nextPage = nextPage
                )
            }
            remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
            recipeListDao.addRecipes(recipes = response)
        }
    }

    override suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecipeItemEntity>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull() ?.let { unsplashImage ->
            remoteKeyDao.getRemoteKeys(id = unsplashImage.recipeId)
        }
    }

    override suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecipeItemEntity>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { unsplashImage ->
            remoteKeyDao.getRemoteKeys(id = unsplashImage.recipeId)
        }
    }

    override suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecipeItemEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.recipeId?.let { recipeId ->
                remoteKeyDao.getRemoteKeys(id = recipeId)
            }
        }
    }
}