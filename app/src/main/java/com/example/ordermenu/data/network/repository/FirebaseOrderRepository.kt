package com.example.ordermenu.data.network.repository

import com.example.ordermenu.domain.model.order.Order
import com.example.ordermenu.domain.model.order.OrderTicket
import com.example.ordermenu.domain.model.order.toOrderTicket
import com.example.ordermenu.domain.repository.OrderRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseOrderRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): OrderRepository {
    private val collectionRef = firestore.collection("orders")
    override fun getAllOrders(): Flow<List<OrderTicket>> = callbackFlow {
        val listenerRegistration = collectionRef.addSnapshotListener{snapshots, e->
            if(e!= null) {
                close(e)
                return@addSnapshotListener
            }
            val orders = snapshots?.documents?.mapNotNull {
                it.toObject(OrderTicket::class.java)
            } ?: emptyList()
            trySend(orders)
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun addOrder(order: Order) {
        if(order.items.isNotEmpty()) {
            try {
                val ticket = order.toOrderTicket()
                collectionRef.document(ticket.id).set(ticket).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
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