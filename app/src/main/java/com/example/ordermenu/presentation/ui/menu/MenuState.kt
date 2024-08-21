package com.example.ordermenu.presentation.ui.menu.menu

import android.net.Uri
import com.example.ordermenu.data.local.table.MenuField
import com.example.ordermenu.data.local.table.MenuItem
import java.util.UUID

data class MenuState(
    val menuItem: Map<MenuField, String> = MenuField.entries.associateWith { "" },
    val uri: Uri? = null,
    val showDialog: Boolean = false
)