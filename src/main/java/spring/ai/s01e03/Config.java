package spring.ai.s01e03;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config
{
    //@Value("openai.api-key")
    private String apiKey="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";

    @Bean
    OpenAiApi openAiApi()
    {
        return new OpenAiApi(apiKey);
    }

    @Bean
    ChatClient defaultOpenAiClient()
    {
        var options = OpenAiChatOptions.builder()
                .withModel("gpt-4")
                .withTemperature(1.0)
                .withMaxTokens(200)
                .build();
        var chatModel = new OpenAiChatModel(openAiApi(), options);
        return ChatClient.builder(chatModel).build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
