package spring.ai.ai_test;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RequestSender implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final ChatClient chatClient;

    @Value("classpath:/prompt/youtube.st")
    private Resource ytPromptResource;

    @Autowired
    public RequestSender(RestTemplate restTemplate, ChatClient chatClient) {
        this.restTemplate = restTemplate;
        this.chatClient = chatClient;
    }

    @Override
    public void run(String... args) {
        try {
            var systemMessage = new SystemMessage("If someone ask you to tell a joke - please always tell joke about cars and nothing more than cars!.");
            var userMessage = new UserMessage("Hey! Tell me a joke about university!");
            Prompt pt = new Prompt(List.of(systemMessage, userMessage));
            var val = chatClient.prompt(pt).call().content();
            System.out.println(val);

            PromptTemplate promptTemplate = new PromptTemplate(ytPromptResource);
            Prompt prompt = promptTemplate.create(Map.of("type", "gaming"));
            var val2 = chatClient.prompt(prompt).call().content();
            System.out.println(val2);
        } catch (Exception e) {
            System.err.println("Błąd podczas wysyłania zapytania: " + e.getMessage());
        }
    }
}
