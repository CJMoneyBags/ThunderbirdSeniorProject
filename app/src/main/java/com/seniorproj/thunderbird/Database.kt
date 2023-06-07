package com.seniorproj.thunderbird

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**************************************************************************************************
 * Contains data classes and methods for using the Database
 *************************************************************************************************/
object Database {
    private const val TAG: String = "DATABASE"

    /**********************************************************************************************
     * Represents a piece of Cargo
     *********************************************************************************************/
    data class Cargo(
        val name: String = "",
        val contents: String = "",
        val length: Double = 0.0,
        val width: Double = 0.0,
        val height: Double = 0.0,
        val weight: Double = 0.0
    )

    /**********************************************************************************************
     * Represents a Container
     *********************************************************************************************/
    data class Container(
        val name: String = "",
        val length: Double = 0.0,
        val width: Double = 0.0,
        val height: Double = 0.0,
        val weightLimit: Double = 0.0
    )

    /**********************************************************************************************
     * Gets a single Cargo document from the database.
     * @param String document name
     * @return [Database.Cargo] or null
     *********************************************************************************************/
    suspend fun getCargo(document: String): Cargo? {
        val data = getDocument("cargo", document)
        return data?.toObject<Cargo>()
    }

    /**********************************************************************************************
     * Gets all Cargo documents from the database.
     * @return [List]<[Database.Cargo]>
     *********************************************************************************************/
    suspend fun getAllCargo(): List<Cargo> {
        val data = getAllDocuments("cargo")
        return data?.documents?.mapNotNull { it.toObject<Cargo>() } ?: listOf()
    }

    /**********************************************************************************************
     * Gets all Cargo documents' names from the database.
     * @return [List]<[String]>
     *********************************************************************************************/
    suspend fun getAllCargoNames(): List<String> {
        return getAllCargo().map { it.name }
    }

    /**********************************************************************************************
     * Puts a new Cargo document into the database.
     * @param Database.Cargo data class
     * @return [Boolean] true if successful, false if not
     *********************************************************************************************/
    suspend fun setCargo(data: Cargo): Boolean {
        return setDocument("cargo", data)
    }

    /**********************************************************************************************
     * Deletes a Cargo document from the database.
     * @param String document name
     * @return [Boolean] true if successful, false if not
     *********************************************************************************************/
    suspend fun deleteCargo(document: String): Boolean {
        return deleteDocument("cargo", document)
    }

    /**********************************************************************************************
     * Gets a single Container document from the database.
     * @param String document name
     * @return [Database.Container] or null
     *********************************************************************************************/
    suspend fun getContainer(document: String): Container? {
        val data = getDocument("containers", document)
        return data?.toObject<Container>()
    }

    /**********************************************************************************************
     * Gets all Container documents from the database.
     * @return [List]<[Database.Container]>
     *********************************************************************************************/
    suspend fun getAllContainers(): List<Container> {
        val data = getAllDocuments("containers")
        return data?.documents?.mapNotNull { it.toObject<Container>() } ?: listOf()
    }

    /**********************************************************************************************
     * Gets all Container documents' names from the database.
     * @return [List]<[String]>
     *********************************************************************************************/
    suspend fun getAllContainerNames(): List<String> {
        return getAllContainers().map { it.name }
    }

    /**********************************************************************************************
     * Puts a new Container document into the database.
     * @param Database.Container data class
     * @return [Boolean] true if successful, false if not
     *********************************************************************************************/
    suspend fun setContainer(data: Container): Boolean {
        return setDocument("containers", data)
    }

    /**********************************************************************************************
     * Deletes a Container document from the database.
     * @param String document name
     * @return [Boolean] true if successful, false if not
     *********************************************************************************************/
    suspend fun deleteContainer(document: String): Boolean {
        return deleteDocument("containers", document)
    }

    /**********************************************************************************************
     * Gets a document from a collection the database.
     * @param String collection name
     * @param String document name
     * @return [DocumentSnapshot]?
     *********************************************************************************************/
    private suspend fun getDocument(collection: String, document: String): DocumentSnapshot? {
        return try {
            val data = Firebase.firestore
                .collection(collection)
                .document(document)
                .get()
                .await()
            data
        }
        catch (e: Exception) {
            Log.d(TAG, "Unable to get $document from $collection due to $e")
            null
        }
    }

    /**********************************************************************************************
     * Gets all documents from a collection in the database.
     * @param String collection name
     * @return [QuerySnapshot]?
     *********************************************************************************************/
    private suspend fun getAllDocuments(collection: String): QuerySnapshot? {
        return try {
            val data = Firebase.firestore
                .collection(collection)
                .get()
                .await()
            data
        }
        catch (e: Exception) {
            Log.d(TAG, "Error: Unable to get all documents from $collection due to $e")
            null
        }
    }

    /**********************************************************************************************
     * Puts a new document into a collection in the database.
     * @param String collection name
     * @param Any Cargo or Container data class
     * @return [Boolean] true if successful, false if not
     *********************************************************************************************/
    private suspend fun setDocument(collection: String, data: Any): Boolean {
        // make sure we have a Cargo or Container object
        val document: String = when (data) {
            is Cargo -> { data.name }
            is Container -> { data.name }
            else -> { return false }
        }

        // put the Cargo or Container into the database
        return try {
            Firebase.firestore
                .collection(collection)
                .document(document)
                .set(data)
                .await()
            true
        }
        catch (e: Exception) {
            Log.d(TAG, "Error: Unable to set data into $document in $collection due to $e")
            false
        }
    }

    /**********************************************************************************************
     * Deletes a document from a collection in the database.
     * @param String collection name
     * @param String document name
     * @return [Boolean] true if successful, false if not
     *********************************************************************************************/
    private suspend fun deleteDocument(collection: String, document: String): Boolean {
        return try {
            Firebase.firestore
                .collection(collection)
                .document(document)
                .delete()
                .await()
            true
        }
        catch (e: Exception) {
            Log.d(TAG, "Error: Unable to delete $document from $collection due to $e")
            false
        }
    }
}