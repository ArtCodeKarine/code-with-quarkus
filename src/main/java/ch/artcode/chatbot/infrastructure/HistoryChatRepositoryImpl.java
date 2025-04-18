package ch.artcode.chatbot.infrastructure;

import ch.artcode.chatbot.domain.HistoryChat;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class HistoryChatRepositoryImpl {
    private final HistoryChatPanacheRepository historyChatPanacheRepository;

    protected PanacheRepository<HistoryChat> panacheRepository() {
        return historyChatPanacheRepository;
    }

    public List<HistoryChat> findByMemoryId(String memoryId) {
        return historyChatPanacheRepository.find("memoryId", memoryId).list();
    }

    public void persist(HistoryChat historyChat) {
        historyChatPanacheRepository.persist(historyChat);
    }

    @ApplicationScoped
    public static class HistoryChatPanacheRepository implements PanacheRepository<HistoryChat> {
    }
}
