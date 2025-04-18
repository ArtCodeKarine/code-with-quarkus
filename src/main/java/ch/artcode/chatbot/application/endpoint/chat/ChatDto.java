package ch.artcode.chatbot.application.endpoint.chat;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(
        name = "ChatDto",
        description = "Dto dedicated to chat responses."
)
public class ChatDto {
    @Schema(
            description = "Answer from the chatbot",
            example = "The meaning of life is 42."
    )
    private String answer;

    public static ChatDto of(String answer) {
        return new ChatDto()
                .setAnswer(answer);
    }
}

