package spring.ai.s01e01;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController
{
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @GetMapping("/jokes")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me what is the capital of Poland?") String message) throws IOException
    {
        Connection.Response pageResponse = Jsoup.connect("https://xyz.ag3nts.org").method(Connection.Method.GET).execute();
        Document page = pageResponse.parse();
        String questionLabel = page.getElementById("human-question").text() + "Podaj tylko rok!";
        String val = chatClient.prompt(questionLabel).call().content();

        Map<String, String> formData = new HashMap<>();
        formData.put("username", "tester");
        formData.put("password", "574e112a");
        formData.put("answer", val);

        Connection.Response response = Jsoup.connect("https://xyz.ag3nts.org")
                .cookies(pageResponse.cookies()) // Use cookies from initial page load
                .data(formData)
                .method(Connection.Method.POST)
                .execute();

        return response.body();
    }
}
