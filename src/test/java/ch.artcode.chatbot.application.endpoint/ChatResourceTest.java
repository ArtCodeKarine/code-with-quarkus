package ch.artcode.chatbot.application.endpoint;


import ch.artcode.chatbot.application.endpoint.chat.ChatDto;
import ch.artcode.chatbot.application.endpoint.chat.ChatRequestDto;
import ch.artcode.chatbot.application.endpoint.chat.ChatResource;
import ch.artcode.chatbot.infrastructure.BotService;
import ch.artcode.chatbot.infrastructure.HistoryChatRepositoryImpl;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestHTTPEndpoint(ChatResource.class)
class ChatResourceTest {
    @Inject
    HistoryChatRepositoryImpl historyChatRepository;

    @InjectMock
    BotService botService;

    @Test
    void CallChatbot_withSimpleQuestion_returnResponseSimpleAndSaveIntoDb() {
        // Given
        ChatRequestDto chatRequestDto = new ChatRequestDto()
                .setLanguage("english")
                .setSessionId("123")
                .setUserName("John")
                .setQuery("Hello");

        String answer = "Hello John, how are you?";
        when(botService.chat(chatRequestDto.getSessionId(), chatRequestDto.getQuery()))
                .thenReturn(answer);
        // When
        ChatDto chatDto = given()
                .contentType(ContentType.JSON)
                .body(chatRequestDto)
                .when()
                .post("/ask")
                .then()
                .statusCode(200)
                .extract()
                .as(ChatDto.class);

        // Then
        assertThat(chatDto).isNotNull();
        assertThat(chatDto.getAnswer()).isNotBlank();
        assertThat(chatDto.getAnswer()).isEqualTo(answer);
        assertThat(historyChatRepository.findByMemoryId("123")).hasSize(1);
    }
}