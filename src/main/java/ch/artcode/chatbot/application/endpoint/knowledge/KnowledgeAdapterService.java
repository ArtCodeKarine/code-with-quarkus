package ch.artcode.chatbot.application.endpoint.knowledge;

import ch.artcode.chatbot.infrastructure.EmbeddingStoreService;
import ch.artcode.chatbot.infrastructure.FileManagerService;
import ch.artcode.chatbot.infrastructure.Knowledge;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class KnowledgeAdapterService {
    private final EmbeddingStoreService embeddingStoreService;
    private final EmbeddingModel embeddingModel;
    private static final String CATALOG_DIRECTORY = "src/main/resources/catalog";
    private static final String CATALOG_DIRECTORY_TEMP = "src/main/resources/catalog-temp";

    public KnowledgeDto addTextSegment(KnowledgeRequestDto knowledgeRequestDto) {
        Embedding embedding = embeddingModel.embed(knowledgeRequestDto.getContent()).content();
        TextSegment textSegment = TextSegment.from(knowledgeRequestDto.getContent());
        Knowledge knowledge = new Knowledge().setEmbedding(embedding).setTextSegment(textSegment);
        return KnowledgeDto.of(embeddingStoreService.addTextIntoMemoryData(knowledge));
    }

    public List<KnowledgeDto> retrieveKnowledge(String search) {
        Embedding embedding = embeddingModel.embed(search).content();
        List<EmbeddingMatch<TextSegment>> segments = embeddingStoreService.retrieveKnowledge(embedding);
        return segments.stream()
                .map(KnowledgeDto::of)
                .collect(Collectors.toList());
    }

    public void addDocument(DocumentDto documentDto) {
        try {
            documentDto.updateAddedDate();
            String fileName = documentDto.getAddedDate() + "_" + documentDto.getFileName();
            FileManagerService.createDirectory(CATALOG_DIRECTORY);
            FileManagerService.saveFileFromBase64(
                    documentDto.getFileData(),
                    CATALOG_DIRECTORY,
                    fileName
            );
            FileManagerService.createDirectory(CATALOG_DIRECTORY_TEMP);
            FileManagerService.saveFileFromBase64(
                    documentDto.getFileData(),
                    CATALOG_DIRECTORY_TEMP,
                    fileName
            );

            List<Document> documents = FileSystemDocumentLoader.loadDocuments(CATALOG_DIRECTORY_TEMP);
            embeddingStoreService.AddDocumentsToKnowledge(documents);

            FileManagerService.deleteDirectory(CATALOG_DIRECTORY_TEMP);

        } catch (IOException e) {
            throw new RuntimeException("Error adding document to catalog", e);
        }
    }
}
