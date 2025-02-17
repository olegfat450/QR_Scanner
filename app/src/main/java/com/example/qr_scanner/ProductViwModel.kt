package com.example.qr_scanner

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.qr_scanner.data.MainDb
import com.example.qr_scanner.data.Product
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel

class ProductViwModel @Inject constructor(val mainDb: MainDb): ViewModel() {

  //  @Inject
  //  lateinit var mainDb: MainDb
 val scope = CoroutineScope(Dispatchers.Main)


    val productList: LiveData<List<Product>>


    init{


        productList = mainDb.dao.getAllProducts()
    }


    fun get() = mainDb.dao.getAllProducts()

    fun deleteProduct(id: Int){ scope.launch (Dispatchers.IO) {  mainDb.dao.deleteProduct(id) }}

    fun insertProduct(product: Product) { scope.launch (Dispatchers.IO) { mainDb.dao.insertProduct(product) }}

}