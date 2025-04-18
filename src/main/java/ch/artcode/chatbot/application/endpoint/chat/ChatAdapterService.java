package ch.artcode.chatbot.application.endpoint.chat;

import ch.artcode.chatbot.infrastructure.BotService;
import ch.artcode.chatbot.infrastructure.HistoryChatRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/*
 * This class is responsible for handling chat requests and responses.
 * It interacts with the BotService to get answers from the chatbot and
 * persists the chat history using the HistoryChatRepositoryImpl.
 */

@ApplicationScoped
@RequiredArgsConstructor
public class ChatAdapterService {
    private final BotService botService;
    private final HistoryChatRepositoryImpl historyChatRepository;

    public ChatDto askingChatbot(ChatRequestDto chatRequestDto) {
        long start = System.currentTimeMillis();
        String answer = botService.chat(chatRequestDto.getSessionId(), chatRequestDto.getQuery());
        persistResponse(chatRequestDto, answer, start);
        return ChatDto.of(answer);
    }

    @Transactional
    void persistResponse(ChatRequestDto chatRequestDto, String answer, long start) {
        historyChatRepository.persist(chatRequestDto.toDomain().setAnswer(answer).setTimestamp(System.currentTimeMillis() - start));
    }
}
