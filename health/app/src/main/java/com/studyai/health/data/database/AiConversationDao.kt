package com.studyai.health.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyai.health.data.model.AiConversation
import com.studyai.health.data.model.AiMessage
import com.studyai.health.data.model.ConversationWithMessages

/**
 * Data Access Object for AI conversations
 */
@Dao
interface AiConversationDao {
    
    // Conversation methods
    @Insert
    suspend fun insertConversation(conversation: AiConversation): Long
    
    @Update
    suspend fun updateConversation(conversation: AiConversation)
    
    @Delete
    suspend fun deleteConversation(conversation: AiConversation)
    
    @Query("SELECT * FROM ai_conversations WHERE id = :id")
    suspend fun getConversationById(id: Int): AiConversation?
    
    @Query("SELECT * FROM ai_conversations ORDER BY updatedAt DESC")
    fun getAllConversations(): LiveData<List<AiConversation>>
    
    @Query("SELECT * FROM ai_conversations WHERE isArchived = 0 ORDER BY updatedAt DESC")
    fun getActiveConversations(): LiveData<List<AiConversation>>
    
    @Query("UPDATE ai_conversations SET isArchived = :isArchived WHERE id = :id")
    suspend fun updateConversationArchiveStatus(id: Int, isArchived: Boolean)
    
    @Query("UPDATE ai_conversations SET updatedAt = :timestamp WHERE id = :id")
    suspend fun updateConversationTimestamp(id: Int, timestamp: Long = System.currentTimeMillis())
    
    // Message methods
    @Insert
    suspend fun insertMessage(message: AiMessage): Long
    
    @Update
    suspend fun updateMessage(message: AiMessage)
    
    @Delete
    suspend fun deleteMessage(message: AiMessage)
    
    @Query("SELECT * FROM ai_messages WHERE id = :id")
    suspend fun getMessageById(id: Int): AiMessage?
    
    @Query("SELECT * FROM ai_messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun getMessagesForConversation(conversationId: Int): LiveData<List<AiMessage>>
    
    @Query("SELECT * FROM ai_messages WHERE conversationId = :conversationId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastMessageForConversation(conversationId: Int): AiMessage?
    
    // Transaction to get conversation with messages
    @Transaction
    @Query("SELECT * FROM ai_conversations WHERE id = :conversationId")
    fun getConversationWithMessages(conversationId: Int): LiveData<ConversationWithMessages>
    
    // Transaction for adding a new message to a conversation
    @Transaction
    suspend fun addMessageToConversation(conversationId: Int, message: String, isUserMessage: Boolean): Long {
        val aiMessage = AiMessage(
            conversationId = conversationId,
            isUserMessage = isUserMessage,
            message = message
        )
        
        // Update conversation timestamp
        updateConversationTimestamp(conversationId)
        
        // Insert message
        return insertMessage(aiMessage)
    }
    
    // Transaction for creating a new conversation with an initial message
    @Transaction
    suspend fun createConversationWithMessage(title: String, message: String): Int {
        // Create conversation
        val conversation = AiConversation(
            title = title
        )
        val conversationId = insertConversation(conversation).toInt()
        
        // Add initial user message
        addMessageToConversation(conversationId, message, true)
        
        return conversationId
    }
} 