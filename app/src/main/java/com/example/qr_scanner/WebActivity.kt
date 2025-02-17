package com.example.qr_scanner

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.qr_scanner.ui.theme.QR_ScannerTheme

class WebActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        setContent {

            Box(modifier = Modifier.fillMaxSize()) {
            
         AndroidView(factory = {WebView(it)},
             update = { it.loadDataWithBaseURL("https://www.google.com/search","4620007592405","text/html","utf-8",null)})

        }}

    }}
