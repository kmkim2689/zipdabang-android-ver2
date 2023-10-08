package com.zipdabang.zipdabang_android.module.drawer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoNicknameEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrect
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrectIcon
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserInfoOneLineScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickCancel : ()->Unit,
    onClickEdit : ()->Unit
) {
    val stateUserInfoOneLine = drawerUserInfoViewModel.stateUserInfoOneLine

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.drawer_edit_oneline)
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color.White
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    .background(Color.White)
                    .fillMaxSize(),
            ){
                Text(
                    text= stringResource(id = R.string.signup_nickname),
                    style = ZipdabangandroidTheme.Typography.twentytwo_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )

                Row(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier.weight(6.8f),
                    ){

                    }
                    Box(
                        modifier = Modifier
                            .weight(3.2f)
                            .wrapContentSize(),
                    ){
                        /*PrimaryButtonOutLined(
                            borderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                            text= stringResource(id = R.string.signup_nickname_deplicatecheck),
                            onClick= {

                            }
                        )*/
                    }
                }
            }

            Row(
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButtonOutLined(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        text= stringResource(id = R.string.drawer_cancel),
                        onClick={
                            onClickCancel()
                        },
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButtonWithStatus(
                        isFormFilled = stateUserInfoOneLine.btnEnabled,
                        text= stringResource(id = R.string.drawer_editdone),
                        onClick={
                            if(stateUserInfoOneLine.isSuccess){
                                CoroutineScope(Dispatchers.Main).launch{
                                    //drawerUserInfoViewModel.patchUserInfoNickname()
                                    onClickEdit()
                                }
                            } else {
                                /*drawerUserInfoViewModel.onUserInfoNicknameEvent(
                                    UserInfoNicknameEvent.BtnEnabled(true))*/
                            }
                        },
                    )
                }
            }
        }
    }
}