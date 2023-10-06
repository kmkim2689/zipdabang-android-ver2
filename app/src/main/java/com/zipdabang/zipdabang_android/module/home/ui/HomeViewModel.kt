package com.zipdabang.zipdabang_android.module.home.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.HomeResource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.home.domain.usecase.GetBestRecipe
import com.zipdabang.zipdabang_android.module.home.domain.usecase.GetHomeBanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeBanner : GetHomeBanner,
    private val getBestRecipes: GetBestRecipe
) :ViewModel(){

    private val _bannerState = mutableStateOf(HomeBannerState())
    var bannerState = _bannerState

    private val _recipeState =  mutableStateOf(HomeRecipeState())
    var recipeState = _recipeState

    init {
        getBannerList()
        getBestRecipe()
    }
    fun getBannerList() {
                getHomeBanner().onEach { result ->
                    when (result) {
                        is HomeResource.HomeSuccess ->{
                            if(result.data?.isSuccess == true){
                                val bannerlist= result.data.result.bannerList
                                Log.e("Bannerresult",result.data.result.toString())
                                _bannerState.value = HomeBannerState(
                                    bannerList = bannerlist,
                                    isLoading = false)
                            }else{
                                Log.e("Home Api Error", result.data!!.message)
                            }
                        }

                        is HomeResource.HomeError ->{
                            _bannerState.value = HomeBannerState(
                                isError = true,
                                error = result.message ?: "An unexpected error occured"
                            )
                        }

                        is HomeResource.HomeLoading-> {
                            _bannerState.value = HomeBannerState(isLoading = true)
                        }


                }

            }.launchIn(viewModelScope)



    }


    fun getBestRecipe(){

        getBestRecipes().onEach { result ->
                    when (result) {

                        is HomeResource.HomeSuccess ->{
                            if(result.data?.isSuccess == true){
                                Log.e("Bannerresult",result.data.result.toString())
                                _recipeState.value =
                                    HomeRecipeState(
                                    recipeList = result.data.result.recipeList,
                                    isLoading = false
                                    )
                            }else{
                                Log.e("Home Api Error", result.data!!.message)
                            }
                        }

                        is HomeResource.HomeError ->{
                            _recipeState.value = HomeRecipeState(
                                isError = true,
                                error = result.data?.message ?: "An unexpected error occured"
                            )
                        }

                        is HomeResource.HomeLoading-> {
                            _recipeState.value = HomeRecipeState(isLoading = true)
                        }

                    }

                }.launchIn(viewModelScope)

            }



}