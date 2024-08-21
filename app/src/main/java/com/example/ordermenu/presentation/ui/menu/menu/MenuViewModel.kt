package com.example.ordermenu.presentation.ui.menu.menu

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermenu.data.local.table.MenuField
import com.example.ordermenu.data.local.table.MenuItem
import com.example.ordermenu.domain.repository.ImageRepository
import com.example.ordermenu.domain.repository.MenuItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val menuItemRepository: MenuItemRepository
): ViewModel(){
    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()

    fun getAllMenuItems(): Flow<List<MenuItem>> =
        menuItemRepository.getAllMenuItems()

    fun toggleDialog() {
        _menuState.update {
            it.copy(
                showDialog = !it.showDialog
            )
        }
    }
    fun updateField(field: MenuField, newValue: String) {
        _menuState.update {
            it.copy(
                menuItem = it.menuItem.toMutableMap().apply {
                    this[field] = newValue
                }
            )
        }
    }

    fun uploadImage(uri: Uri?) {
        viewModelScope.launch {
            uri?.let {
                val downloadUrl = imageRepository.uploadImage(uri)
                downloadUrl?.let {
                    updateField(MenuField.IMAGE_URL, it)
                }
            }
        }
    }

    fun addMenuItem() = viewModelScope.launch{
//        menuItemRepository.addMenuItem(_menuState.value.menuItem)
//        _menuState.update {
//            it.copy(
//                menuItem = MenuItem()
//            )
//        }
        toggleDialog()
    }
}