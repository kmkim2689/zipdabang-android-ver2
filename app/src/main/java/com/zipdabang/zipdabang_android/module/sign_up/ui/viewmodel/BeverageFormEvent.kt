package com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel

sealed class BeverageFormEvent{
    data class BeverageCheckListChanged(val index : Int, val checked : Boolean) : BeverageFormEvent()
    data class BtnChanged(val enabled: Boolean) : BeverageFormEvent()
}
