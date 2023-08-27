package com.zipdabang.zipdabang_android.module.recipes.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.mapper.toPreferenceToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ToggleScrapUseCase @Inject constructor(
    private val tokenDataStore: DataStore<Token>,
    private val repository: PreferenceToggleRepository
) {
    operator fun invoke(
        recipeId: Int
    ): Flow<Resource<PreferenceToggle>> = flow {
        try {
            val accessToken = tokenDataStore.data.first().accessToken ?: TOKEN_NULL

            emit(Resource.Loading())

            val scrapToggleResult = repository.toggleScrap(
                accessToken = accessToken,
                recipeId = recipeId
            ).toPreferenceToggle()

            emit(
                Resource.Success(
                    data = scrapToggleResult,
                    code = scrapToggleResult.code,
                    message = scrapToggleResult.message
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, check internet connection"))
        }
    }
}