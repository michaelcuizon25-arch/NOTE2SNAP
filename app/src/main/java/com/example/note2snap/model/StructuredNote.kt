package com.example.note2snap.model

enum class BlockType {
    TITLE,
    SECTION_HEADER,
    BULLET_ITEM,
    KEY_DEFINITION,
    MATHEMATICAL,
    REGULAR_TEXT
}

data class NoteBlock(
    val rawText: String,
    val type: BlockType,
    val formattedText: String
)

data class StructuredNote(
    val title: String,
    val blocks: List<NoteBlock>
)