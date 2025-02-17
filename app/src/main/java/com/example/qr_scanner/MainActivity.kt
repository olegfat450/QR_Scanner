package com.example.qr_scanner

import android.app.Application
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qr_scanner.data.MainDb
import com.example.qr_scanner.data.Product
import com.example.qr_scanner.ui.theme.QR_ScannerTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

//class UserViewModelFactory (val application: Application): ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return ProductViwModel (application) as T
//    }
//}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


   private lateinit var scope: CoroutineScope
    private lateinit var  productList: State<List<Product>>
    val text = mutableStateOf("Result")
  var count = 0
    // var s: Product? = null
    var colorBackground =  mutableStateOf(Color.LightGray)
       private lateinit var vm: ProductViwModel

       var content = mutableStateOf("error")

            val scanLauncher = registerForActivityResult(ScanContract()){ result ->

        if (result.contents != null) { val s = productList.value.firstOrNull{ t -> t.QR == result.contents}

             if (s != null) { var v = s.value; v++; vm.insertProduct(Product(s.id,v,result.contents))} else
                 vm.insertProduct(Product(null,1,result.contents));

                   content.value = result.contents
                    scope.launch { colorBackground.value = Color.Green; delay(2000); colorBackground.value = Color.LightGray }
        }
            }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


//            val owner = LocalViewModelStoreOwner.current
//            owner?.let {
//                vm = viewModel(it,"ProductViewModel",UserViewModelFactory(
//                    LocalContext.current.applicationContext as Application))
//
//            }
             vm = viewModel()

             // val vm: ProductViwModel = viewModel()

             productList = vm.productList.observeAsState(listOf())
            var menu by remember { mutableStateOf(false) }
             scope = rememberCoroutineScope()
           //  val productList = mainDb.dao.getAllProducts().collectAsState(initial = emptyList())
            var selected by remember { mutableStateOf(-1) }







            QR_ScannerTheme {
                Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {

                  LazyColumn (modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)) {

                      items(productList.value) {

                          Row (modifier = Modifier.fillParentMaxWidth().padding(8.dp)
                              .clickable(onClick = { content.value = it.QR; val intent = Intent(Intent.ACTION_WEB_SEARCH)
                                  intent.putExtra(SearchManager.QUERY,content.value)
                              startActivity(intent)
                              })
                              .background(color = if (it.QR == content.value) colorBackground.value else Color.LightGray), horizontalArrangement = Arrangement.SpaceBetween){
                              Text(text = "${it.id}  -  ${it.QR}", fontSize = 22.sp, modifier = Modifier.padding(8.dp).fillMaxWidth(0.8f))



                              Row { Text(text = "${it.value}", fontSize = 22.sp, modifier = Modifier.padding(8.dp))
                             Box()  {
                                 Icon(Icons.Filled.Delete,
                                     contentDescription = "",
                                     modifier = Modifier.padding(8.dp)
                                         .clickable(onClick = { selected = it.id!! })
                                 )

                                 DropdownMenu(expanded = (selected == it.id), onDismissRequest = { selected = -1 }) {
                                     DropdownMenuItem( { Text(text = "Delete item   ${it.id}", fontSize = 22.sp)}, onClick = { vm.deleteProduct(it.id!!) ; selected = -1})
                                     Divider()
                                     DropdownMenuItem( { Text(text = "Cancel", fontSize = 22.sp) } , onClick = {selected = -1}) }
                             }

                              }

                          }
                      }
                  }

                    Button(onClick = {

                         scan()

                    //    val s = productList.firstOrNull{ it.id == 12 }



                     //   if (s != null)  { var v = s.value; v++

                          //  Toast.makeText(this@MainActivity,"${v}",Toast.LENGTH_LONG).show()


                     //       vm.insertProduct(Product(s.id,v,"asd"))
                      //  } else

                     //   { vm.insertProduct(
                     //   Product(
                      //  null,1,"abs")) }

                       //  scope.launch { colorBackground = Color.Green; delay(2000); colorBackground = Color.LightGray }

                    }) { Text(text = "Scan") }


                }




    }
}




    }

    private fun scan() {

        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Scan a barcode")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)

        scanLauncher.launch(options)
    }
}












