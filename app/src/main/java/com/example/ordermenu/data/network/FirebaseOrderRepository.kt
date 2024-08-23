package com.example.ordermenu.data.network

import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.model.order.toOrderTicket
import com.example.ordermenu.domain.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseOrderRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): OrderRepository {
    private val collectionRef = firestore.collection("orders")
    override suspend fun getAllOrders(): List<OrderTicket> {
        return try {
            val snapshot = collectionRef.get().await()
            snapshot.documents.mapNotNull {
                it.toObject(OrderTicket::class.java)
            }
        } catch(e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun addOrder(order: Order) {
        try {
            val ticket = order.toOrderTicket()
            collectionRef.document(ticket.id).set(ticket).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun removeOrder(id: String) {
        try {
            collectionRef.document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}