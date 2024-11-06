package spring.ai.s01e02;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestSender implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final ChatClient chatClient;

    @Autowired
    public RequestSender(RestTemplate restTemplate, ChatClient chatClient) {
        this.restTemplate = restTemplate;
        this.chatClient = chatClient;
    }

    @Override
    public void run(String... args) {
        // URL endpointu
        String url = "https://xyz.ag3nts.org/verify";

        // Tworzenie obiektu JSON jako payload
        Payload requestBody = new Payload("READY", "0");

        // Tworzenie nagłówków żądania
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Utworzenie pełnego obiektu żądania
        HttpEntity<Payload> request = new HttpEntity<>(requestBody, headers);

        // Wysłanie żądania POST i odbiór odpowiedzi
        try {
            Payload responsePayload = restTemplate.postForObject(url, request, Payload.class);
            System.out.println(responsePayload.getText());
            String val = chatClient.prompt("Please always answer in english! For this conversation, please remember that: The capital of Poland is Kraków.\n" +
                    "The well-known number from the book The Hitchhiker's Guide to the Galaxy is 69.\n" +
                    "The current year is 1999. " +
                    "Question: " + responsePayload.getText() + "And please remember - answer always in English!").call().content();
            System.out.println(val);

            Payload responsePayload2 = restTemplate.postForObject(url, new Payload(val, responsePayload.getMsgID()), Payload.class);
            System.out.println(responsePayload2.getText());
        } catch (Exception e) {
            System.err.println("Błąd podczas wysyłania zapytania: " + e.getMessage());
        }
    }

    private static class Payload {
        private String text;
        private String msgID;

        public Payload(String text, String msgID) {
            this.text = text;
            this.msgID = msgID;
        }

        // Gettery są wymagane przez WebClient do serializacji obiektu jako JSON
        public String getText() {
            return text;
        }

        public String getMsgID() {
            return msgID;
        }
    }
}
