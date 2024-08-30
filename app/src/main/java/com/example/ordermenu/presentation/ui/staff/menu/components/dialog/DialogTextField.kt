package com.example.ordermenu.presentation.ui.staff.menu.components.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        keyboardOptions = keyboardOptions,
        modifier = Modifier.padding(4.dp)
    )
}