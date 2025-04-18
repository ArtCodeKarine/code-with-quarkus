package ch.artcode.chatbot.infrastructure;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkiverse.langchain4j.chroma.ChromaEmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@ApplicationScoped
@RequiredArgsConstructor
public class EmbeddingStoreService {
    private final ChromaEmbeddingStore chromaEmbeddingStore;
    private final EmbeddingModel embeddingModel;

    public Knowledge addTextIntoMemoryData(Knowledge knowledge) {
        String id = chromaEmbeddingStore.add(knowledge.getEmbedding(), knowledge.getTextSegment());
        knowledge.setId(id);
        return knowledge;
    }

    public void AddDocumentsToKnowledge(List<Document> documents) {
        EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(chromaEmbeddingStore)
                .embeddingModel(embeddingModel)
                .documentSplitter(recursive(500, 0))
                .build();
        embeddingStoreIngestor.ingest(documents);
    }

    public List<EmbeddingMatch<TextSegment>> retrieveKnowledge(Embedding embedding) {
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .build();
        EmbeddingSearchResult<TextSegment> result = chromaEmbeddingStore.search(embeddingSearchRequest);
        return result.matches();
    }
}
