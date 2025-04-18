package ch.artcode.chatbot.infrastructure;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Knowledge {
    private String id;
    private Embedding embedding;
    private TextSegment textSegment;
}
