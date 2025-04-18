package ch.artcode.chatbot.application.endpoint;

import ch.artcode.chatbot.application.endpoint.knowledge.KnowledgeDto;
import ch.artcode.chatbot.application.endpoint.knowledge.KnowledgeRequestDto;
import ch.artcode.chatbot.application.endpoint.knowledge.KnowledgeResource;
import ch.artcode.chatbot.application.endpoint.knowledge.KnowledgeSearchDto;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestHTTPEndpoint(KnowledgeResource.class)
public class KnowledgeResourceTest {

    @Test
    void addEmbedding_withSimpleText_returnsSuccess() {
        // Given
        String dataToAdd = "Mon chat s'appelle Rose";
        KnowledgeRequestDto memoryDataRequestDto = new KnowledgeRequestDto()
                .setContent(dataToAdd);

        // When
        KnowledgeDto knowledgeDto = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(memoryDataRequestDto)
                .post("/text")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .as(KnowledgeDto.class);

        // Then
        assertThat(knowledgeDto.getId()).isNotBlank();
        assertThat(knowledgeDto.getContent()).isNotBlank();
        assertThat(knowledgeDto.getContent()).isEqualTo(dataToAdd);
    }

    @Test
    void findEmbedding_searchWithWord_returnsResults() {
        // Given: Add data
        String dataToAdd = "Mon chat s'appelle Rose";
        KnowledgeRequestDto knowledgeRequestDto = new KnowledgeRequestDto()
                .setContent(dataToAdd);

        KnowledgeDto addedData = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(knowledgeRequestDto)
                .post("/text")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .as(KnowledgeDto.class);

        assertThat(addedData).isNotNull();
        assertThat(addedData.getId()).isNotBlank();

        // Given: Search
        String search = "chat";
        KnowledgeSearchDto knowledgeSearchDto = new KnowledgeSearchDto()
                .setSearch(search);

        // When
        List<KnowledgeDto> knowledgeDtos = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(knowledgeSearchDto)
                .post()
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", KnowledgeDto.class);

        // Then
        assertThat(knowledgeDtos).isNotEmpty();

        List<KnowledgeDto> sortedResult = new ArrayList<>(knowledgeDtos);
        sortedResult.sort((dto1, dto2) -> Double.compare(dto2.getScore(), dto1.getScore()));
        KnowledgeDto topResult = sortedResult.getFirst();
        assertThat(topResult.getId()).isNotBlank();
        assertThat(topResult.getContent()).isNotBlank();
        assertThat(topResult.getContent()).contains(search);
    }
}
