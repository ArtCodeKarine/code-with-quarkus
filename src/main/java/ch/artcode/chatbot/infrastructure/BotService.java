package ch.artcode.chatbot.infrastructure;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * This is a sample Bot, it is configured to ingest the 'easy-rag-catalog/'.
 * You can @Inject this Bot in your Rest resource and use it.
 * \{@code
 *
 * @Inject BotService bot;
 * @POST
 * @Produces(MediaType.TEXT_PLAIN) public String chat(String q) {
 * return bot.chat(q);
 * }
 * }
 */
@SuppressWarnings("CdiManagedBeanInconsistencyInspection" /** This is a false positive. */)
@RegisterAiService
@ApplicationScoped
public interface BotService {

    @SystemMessage("""
            You are an AI named Bob answering questions about financial products.
            Your response must be polite, use the same language as the question, and be relevant to the question.
            
            When you don't know, respond that you don't know the answer and the bank will contact the customer directly.
            """)
    String chat(@MemoryId String id, @UserMessage String question);
}