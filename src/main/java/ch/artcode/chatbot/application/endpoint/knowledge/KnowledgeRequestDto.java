package ch.artcode.chatbot.application.endpoint.knowledge;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(
        name = "KnowledgeRequestDto",
        description = "Dto dedicated to knowledge requests."
)
public class KnowledgeRequestDto {
    @Schema(
            description = "Use to add text a new knowledge",
            example = "The meaning of life is 42"
    )
    private String content;
}

