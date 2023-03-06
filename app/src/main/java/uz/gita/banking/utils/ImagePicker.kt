package uz.gita.banking.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import uz.gita.banking.R
import java.io.File


class ComposeFileProvider : FileProvider(
    R.xml.filepath
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File(directory, "image.jpg")
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}


class ImagePicker() {
    private var imageUri: ((Uri?) -> Unit)? = null


    fun captureListener(block: (Uri?) -> Unit) {
        imageUri = {
            block(it)
        }
    }


    @Composable
    fun takePicture(
    ) {
        val cropImage = rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                val uriContent = result.uriContent
                imageUri?.let { it(uriContent) }
            } else {
                val exception = result.error
            }
        }
        var shouldShowDialog by remember {
            mutableStateOf(true)
        }
        val context = LocalContext.current
        val uri = ComposeFileProvider.getImageUri(context)
        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uris ->
                uris?.let {
                    cropImage.launch(
                        CropImageContractOptions(
                            it, cropImageOptions = CropImageOptions()
                        )
                    )
                }
            })
        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if (success) {
                    File(context.filesDir, "image.jpg").delete()
                    cropImage.launch(
                        CropImageContractOptions(
                            uri, cropImageOptions = CropImageOptions()
                        )
                    )
                }
            })
        var deleteDialog by remember {
            mutableStateOf(false)
        }


        if (deleteDialog) {
            AlertDialog(
                onDismissRequest = { deleteDialog = false },
                title = { Text(text = "Waring") },
                text = { Text(text = "Are you sure want to delete?") },
                confirmButton = {
                    TextButton(onClick = {
                        imageUri?.let { it(null) }
                        deleteDialog = false
                    }) {
                        Text(text = "Ok")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        deleteDialog = false
                    }) {
                        Text(text = "Cancel")
                    }
                },
            )
        }

        if (shouldShowDialog) {
            Dialog(onDismissRequest = {
                shouldShowDialog = false
            }) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            shouldShowDialog = false
                            cameraLauncher.launch(uri)
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "camera"
                        )
                        Text(text = "Camera")
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            shouldShowDialog = false
                            imagePicker.launch("image/*")
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = "gallery"
                        )
                        Text(text = "Gallery")
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            deleteDialog = true
                            shouldShowDialog = false
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(30.dp),
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "gallery"
                        )
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}





