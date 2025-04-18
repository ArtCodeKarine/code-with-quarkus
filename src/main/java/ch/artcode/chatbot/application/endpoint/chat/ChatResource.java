package ch.artcode.chatbot.application.endpoint.chat;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Chat", description = "API to communicate with the chatbot")
@Path("/v1/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ChatResource {
    private final ChatAdapterService chatAdapterService;

    @Operation(
            summary = "Permet de demander une réponse au chatbot",
            description = "Permet de demander une réponse au chatbot."
    )
    @POST
    @Path("/ask")
    public ChatDto askingChatbot(ChatRequestDto chatRequestDto) {
        return chatAdapterService.askingChatbot(chatRequestDto);
    }
}
