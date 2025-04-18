package ch.artcode.chatbot.application.endpoint.knowledge;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Tag(name = "knowledge", description = "API to add and retrieve knowledge data")
@Path("/v1/knowledge")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class KnowledgeResource {
    private final KnowledgeAdapterService knowledgeAdapterService;

    @Operation(
            summary = "Get knowledge data",
            description = "Get all information into knowledge data."
    )
    @POST
    public List<KnowledgeDto> getKnowledge(KnowledgeSearchDto knowledgeSearchDto) {
        return knowledgeAdapterService.retrieveKnowledge(knowledgeSearchDto.getSearch());
    }

    @Operation(
            summary = "Add a text to memory data",
            description = "Add a text to memory data."
    )
    @POST
    @Path("/text")
    public KnowledgeDto addTextToMemoryData(KnowledgeRequestDto knowledgeRequestDto) {
        return knowledgeAdapterService.addTextSegment(knowledgeRequestDto);
    }

    @Operation(
            summary = "Add a document to memory data",
            description = "Add a document to memory data."
    )
    @POST
    @Path("/document")
    public void addDocumentToMemoryData(DocumentDto documentDto) {
        knowledgeAdapterService.addDocument(documentDto);
    }
}
