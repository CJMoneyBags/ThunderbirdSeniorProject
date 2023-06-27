package com.seniorproj.thunderbird

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.parcelize.Parcelize

/**************************************************************************************************
 * Contains data classes and methods for using the Database
 *************************************************************************************************/
object Database {
    private const val TAG: String = "DATABASE"

    /**********************************************************************************************
     * Represents a piece of Cargo
     *********************************************************************************************/
    @Parcelize
    data class Cargo(
        val name: String = "",
        val length: Double = 0.0,
        val width: Double = 0.0,
        val height: Double = 0.0,
        val weight: Double = 0.0
    ): Parcelable {
        override fun toString(): String {
            return name
        }
    }

    /**********************************************************************************************
     * Represents a Container
     *********************************************************************************************/
    @Parcelize
    data class Container(
        val name: String = "",
        val length: Double = 0.0,
        val width: Double = 0.0,
        val height: Double = 0.0,
        val weightLimit: Double = 0.0
    ): Parcelable {
        override fun toString(): String {
            return name
        }
    }

    /**********************************************************************************************
     * Represents a Parcelable version of Pair<Database.Cargo, Int>
     *********************************************************************************************/
    @Parcelize
    data class CargoPair(val cargo: Cargo, val number: Int): Parcelable {
        override fun toString(): String {
            return "$number of \"$cargo\""
        }
    }

    /**********************************************************************************************
     * Gets a single Cargo document from the database.
     * @param document document name
     * @return [Database.Cargo] or null if nonexistent
     *********************************************************************************************/
    suspend fun getCargo(document: String): Cargo? {
        // a DocumentSnapshot translated into a Cargo object
        return getDocument("cargo", document)?.toObject<Cargo>()
    }

    /**********************************************************************************************
     * Gets all Cargo documents from the database.
     * @return [List]<[Database.Cargo]> or null if nonexistent
     *********************************************************************************************/
    suspend fun getAllCargo(): List<Cargo>? {
        // a list of DocumentSnapshots translated into a list of Cargo objects
        return getAllDocuments("cargo")?.mapNotNull { it.toObject<Cargo>() }
    }

    /**********************************************************************************************
     * Gets all Cargo documents' names from the database.
     * @return [List]<[String]> or null if nonexistent
     *********************************************************************************************/
    suspend fun getAllCargoNames(): List<String>? {
        // a list of Cargo all mapped to be their names
        return getAllCargo()?.map { it.name }
    }

    /**********************************************************************************************
     * Puts a new Cargo document into the database.
     * @param data [Database.Cargo]
     * @return true if successful, false if not
     *********************************************************************************************/
    suspend fun setCargo(data: Cargo): Boolean {
        return setDocument("cargo", data)
    }

    /**********************************************************************************************
     * Deletes a Cargo document from the database.
     * @param document document name
     * @return true if successful, false if not
     *********************************************************************************************/
    suspend fun deleteCargo(document: String): Boolean {
        return deleteDocument("cargo", document)
    }

    /**********************************************************************************************
     * Gets a single Container document from the database.
     * @param document document name
     * @return [Database.Container] or null if nonexistent
     *********************************************************************************************/
    suspend fun getContainer(document: String): Container? {
        // a DocumentSnapshot translated into a Container object
        return getDocument("containers", document)?.toObject<Container>()
    }

    /**********************************************************************************************
     * Gets all Container documents from the database.
     * @return [List]<[Database.Container]> or null if nonexistent
     *********************************************************************************************/
    suspend fun getAllContainers(): List<Container>? {
        // a list of DocumentSnapshots translated into a list of Container objects
        return getAllDocuments("containers")?.mapNotNull { it.toObject<Container>() }
    }

    /**********************************************************************************************
     * Gets all Container documents' names from the database.
     * @return [List]<[String]> or null if nonexistent
     *********************************************************************************************/
    suspend fun getAllContainerNames(): List<String>? {
        // a list of Containers all mapped to be their names
        return getAllContainers()?.map { it.name }
    }

    /**********************************************************************************************
     * Puts a new Container document into the database.
     * @param data [Database.Container]
     * @return true if successful, false if not
     *********************************************************************************************/
    suspend fun setContainer(data: Container): Boolean {
        return setDocument("containers", data)
    }

    /**********************************************************************************************
     * Deletes a Container document from the database.
     * @param document document name
     * @return true if successful, false if not
     *********************************************************************************************/
    suspend fun deleteContainer(document: String): Boolean {
        return deleteDocument("containers", document)
    }

    /**********************************************************************************************
     * Gets a document from a collection the database.
     * @param collection collection name
     * @param document document name
     * @return [DocumentSnapshot]?
     *********************************************************************************************/
    private suspend fun getDocument(collection: String, document: String): DocumentSnapshot? {
        return try {
            val data = Firebase.firestore
                .collection(collection)
                .document(document)
                .get()
                .await()
            Log.d(TAG, "Retrieved $document from $collection successfully.")
            data
        }
        catch (e: Exception) {
            Log.d(TAG, "Unable to get $document from $collection due to $e.")
            null
        }
    }

    /**********************************************************************************************
     * Gets all documents from a collection in the database.
     * @param collection collection name
     * @return [List]<[DocumentSnapshot]>?
     *********************************************************************************************/
    private suspend fun getAllDocuments(collection: String): List<DocumentSnapshot>? {
        return try {
            val data = Firebase.firestore
                .collection(collection)
                .get()
                .await()
            Log.d(TAG, "Retrieved all documents from $collection successfully.")
            data.documents
        }
        catch (e: Exception) {
            Log.d(TAG, "Error: Unable to get all documents from $collection due to $e.")
            null
        }
    }

    /**********************************************************************************************
     * Puts a new document into a collection in the database.
     * @param collection collection name
     * @param data Cargo or Container data class
     * @return true if successful, false if not
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
            Log.d(TAG, "Data set into $document in $collection successfully.")
            true
        }
        catch (e: Exception) {
            Log.d(TAG, "Error: Unable to set data into $document in $collection due to $e.")
            false
        }
    }

    /**********************************************************************************************
     * Deletes a document from a collection in the database.
     * @param collection collection name
     * @param document document name
     * @return true if successful, false if not
     *********************************************************************************************/
    private suspend fun deleteDocument(collection: String, document: String): Boolean {
        return try {
            Firebase.firestore
                .collection(collection)
                .document(document)
                .delete()
                .await()
            Log.d(TAG, "Deleted $document from $collection successfully.")
            true
        }
        catch (e: Exception) {
            Log.d(TAG, "Error: Unable to delete $document from $collection due to $e.")
            false
        }
    }
}
