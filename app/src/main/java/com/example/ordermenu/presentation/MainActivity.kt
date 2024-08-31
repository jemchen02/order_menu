package com.example.ordermenu.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ordermenu.presentation.ui.auth.AuthPage
import com.example.ordermenu.presentation.ui.auth.AuthViewModel
import com.example.ordermenu.presentation.ui.staff.StaffPage
import com.example.ordermenu.presentation.ui.staff.menu.MenuPage
import com.example.ordermenu.presentation.ui.staff.tickets.TicketsPage
import com.example.ordermenu.presentation.ui.theme.OrderMenuTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AuthViewModel
    private val barCodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "QR Code found", Toast.LENGTH_LONG).show()
            viewModel.setRestaurantId(result.contents)
        }
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)
        options.setTimeout(20000)
        barCodeLauncher.launch(options)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showCamera()
        }
    }
    private fun checkCameraPermission(context: Context) {
        if(ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        }
        else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Camera required", Toast.LENGTH_LONG).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            viewModel = hiltViewModel<AuthViewModel>()
            OrderMenuTheme {
                AuthPage(
                    viewModel = viewModel,
                    scanCode = { checkCameraPermission(this) }
                )
            }
        }
    }
}