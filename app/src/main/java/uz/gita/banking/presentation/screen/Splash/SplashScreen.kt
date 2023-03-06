package uz.gita.banking.presentation.screen.Splash

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.banking.presentation.screen.Splash.vm.SplashUiState
import uz.gita.banking.presentation.screen.Splash.vm.SplashVM
import uz.gita.banking.presentation.screen.Splash.vm.impl.SplashVMImpl
import uz.gita.banking.ui.theme.BankingTheme
import uz.gita.banking.utils.ImagePicker


class SplashScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: SplashVM = getViewModel<SplashVMImpl>()
        val uiState = viewModel.collectAsState().value
        SplashScreenComponent(uiState = uiState)
    }

    @Composable
    fun SplashScreenComponent(uiState: SplashUiState) {

        var image by remember {
            mutableStateOf<Uri?>(null)
        }

        val pick = ImagePicker()
        pick.takePicture()

     pick.captureListener {
       Log.d("TTT","ishladi")
     }

//        val cropImage = rememberLauncherForActivityResult(CropImageContract()) { result ->
//            if (result.isSuccessful) {
//                val uriContent = result.uriContent
//                image = uriContent
//            } else {
//                val exception = result.error
//            }
//        }
//
//        cropImage.launch(
//            CropImageContractOptions(
//                pick.getImage(),
//                cropImageOptions = CropImageOptions()
//            )
//        )
//        val configuration = LocalConfiguration.current
//        val screenHeight = configuration.screenHeightDp.dp
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = SplashBackground)
//                .padding(20.dp),
//        ) {
//            Spacer(
//                Modifier.weight(1f)
//            )
//            GitaLogo(logoSize = 30.dp, textSize = 30.sp, textColor = Color.White)
//            Spacer(Modifier.weight(1f))
//
//            Text(text = "Version ${uiState.version}", Modifier, color = Color.White)
//        }
    }

    @Preview
    @Composable
    fun SplashScreenView() {
        BankingTheme {
//            SplashScreenComponent()
        }
    }

}
