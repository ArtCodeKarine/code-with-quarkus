package ch.artcode.chatbot.application.endpoint.knowledge;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(
        name = "KnowledgeSearchDto",
        description = "Dto dedicated to knowledge search."
)
public class KnowledgeSearchDto {
    @Schema(
            description = "Search query",
            example = "life"
    )
    private String search;
}
