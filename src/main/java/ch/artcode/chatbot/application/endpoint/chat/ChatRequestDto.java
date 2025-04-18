package ch.artcode.chatbot.application.endpoint.chat;

import ch.artcode.chatbot.domain.HistoryChat;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(
        name = "ChatRequestDto",
        description = "Dto dedicated to chat requests."
)
public class ChatRequestDto {
    @Schema(
            description = "Session identifier",
            example = "1fmwpef-efwe8m-efwef8"
    )
    private String sessionId;

    @Schema(
            description = "Question asked to the chatbot",
            example = "What is the meaning of life?"
    )
    private String query;

    @Schema(
            description = "Language of the question",
            example = "fr"
    )
    private String language;

    @Schema(
            description = "User identifier",
            example = "Tom"
    )
    private String userName;

    public HistoryChat toDomain() {
        return new HistoryChat()
                .setQuery(query)
                .setMemoryId(sessionId)
                ;
    }
}
