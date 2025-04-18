package ch.artcode.chatbot.application.endpoint.knowledge;

import ch.artcode.chatbot.infrastructure.Knowledge;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Map;

@Data
@Schema(
        name = "KnowledgeDto",
        description = "Dto dedicated to knowledge management."
)
public class KnowledgeDto {
    @Schema(
            description = "Id of the knowledge",
            example = "1fmwpef-efwe8m-efwef8"
    )
    private String id;

    @Schema(
            description = "knowledge content",
            example = "This is a text"
    )
    private String content;

    @Schema(
            description = "Metadata",
            example = "{ \"key\": \"value\" }"
    )
    private Map<String, Object> metadata;

    @Schema(
            description = "Score of the match",
            example = "0.8"
    )
    private Double score;

    @Schema(
            description = "Dimension of the embedding",
            example = "0"
    )
    private int dimension;

    public static KnowledgeDto of(Knowledge knowledge) {
        return new KnowledgeDto()
                .setId(knowledge.getId())
                .setContent(knowledge.getTextSegment().text());
    }

    public static KnowledgeDto of(EmbeddingMatch<TextSegment> embeddingMatch) {
        return new KnowledgeDto()
                .setContent(embeddingMatch.embedded().text())
                .setMetadata(embeddingMatch.embedded().metadata().toMap())
                .setId(embeddingMatch.embeddingId())
                .setScore(embeddingMatch.score())
                .setDimension(embeddingMatch.embedding().dimension())
                ;
    }

}

