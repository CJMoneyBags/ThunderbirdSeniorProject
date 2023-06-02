package com.seniorproj.thunderbird

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

object Database {
    data class Cargo(
        val name: String = "",
        val contents: String = "",
        val length: Double = 0.0,
        val width: Double = 0.0,
        val height: Double = 0.0,
        val weight: Double = 0.0
    )

    data class Container(
        val name: String = "",
        val length: Double = 0.0,
        val width: Double = 0.0,
        val height: Double = 0.0,
        val weightLimit: Double = 0.0
    )

    suspend fun getCargo(document: String): Cargo? {
        val data = getData("cargo", document)
        return data?.toObject<Cargo>()
    }

    suspend fun setCargo(data: Cargo): Boolean {
        val cargo = mapOf<String, Any>(
            "name" to data.name,
            "contents" to data.contents,
            "length" to data.length,
            "width" to data.width,
            "height" to data.height,
            "weight" to data.weight
        )
        return setData("cargo", data.name, cargo)
    }

    suspend fun getContainer(document: String): Container? {
        val data = getData("containers", document)
        return data?.toObject<Container>()
    }

    suspend fun setContainer(data: Container): Boolean {
        val container = mapOf<String, Any>(
            "name" to data.name,
            "length" to data.length,
            "width" to data.width,
            "height" to data.height,
            "weightLimit" to data.weightLimit
        )
        return setData("containers", data.name, container)
    }

    private suspend fun getData(collection: String, document: String): DocumentSnapshot? {
        return try {
            val data = Firebase.firestore
                .collection(collection)
                .document(document)
                .get()
                .await()
            data
        }
        catch (e: Exception) {
            null
        }
    }

    private suspend fun setData(collection: String, document: String, data: Map<String, Any>): Boolean {
        return try {
            Firebase.firestore
                .collection(collection)
                .document(document)
                .set(data)
                .await()
            true
        }
        catch (e: Exception) {
            false
        }
    }
}