package com.studyai.health.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * Entity representing a conversation with the AI
 */
@Entity(tableName = "ai_conversations")
data class AiConversation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false
)

/**
 * Entity representing a message in an AI conversation
 */
@Entity(tableName = "ai_messages")
data class AiMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val conversationId: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val isUserMessage: Boolean,
    val message: String,
    val relatedHealthRecordId: Int? = null
)

/**
 * Data class representing a conversation with its messages
 */
data class ConversationWithMessages(
    @Relation(
        parentColumn = "id",
        entityColumn = "conversationId"
    )
    val conversation: AiConversation,
    val messages: List<AiMessage>
)

/**
 * Represents a response from the AI service
 */
data class AiResponse(
    val message: String,
    val recommendation: Recommendation? = null,
    val healthContext: Map<String, Any>? = null
) 