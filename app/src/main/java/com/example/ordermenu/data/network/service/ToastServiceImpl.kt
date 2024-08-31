package com.example.ordermenu.data.network.service

import android.content.Context
import android.widget.Toast
import com.example.ordermenu.domain.service.ToastService
import javax.inject.Inject

class ToastServiceImpl @Inject constructor(
    private val context: Context
) : ToastService{
    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}