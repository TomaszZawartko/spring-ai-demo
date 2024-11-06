package spring.ai.s01e03;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalibrationDataService  implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final ChatClient chatClient;

    public CalibrationDataService(RestTemplate restTemplate, ChatClient chatClient)
    {
        this.restTemplate = restTemplate;
        this.chatClient = chatClient;
    }

    public CalibrationData fetchCalibrationData() {
        String url = "https://centrala.ag3nts.org/data/cb998dce-22dc-4768-82af-fb097703a5d0/json.txt";
        CalibrationData forObject = restTemplate.getForObject(url, CalibrationData.class);
        return forObject;
    }

    @Override
    public void run(String... args) throws Exception
    {
        CalibrationData inputData = fetchCalibrationData();
        CalibrationData outputData = new CalibrationData();
        outputData.setApikey("cb998dce-22dc-4768-82af-fb097703a5d0");
        outputData.setDescription(inputData.getDescription());
        outputData.setCopyright(inputData.getCopyright());

        List<TestData> outputTestData = new ArrayList<>();
        for(TestData testData : inputData.getTestData())
        {
            if(testData.getTest() != null)
            {
                String question = testData.getTest().getQ();
                String answer = chatClient.prompt(question).call().content();
                InnerTest correctInnerTest = new InnerTest();
                correctInnerTest.setQ(question);
                correctInnerTest.setA(answer);
                TestData correctTestData = new TestData();
                correctTestData.setQuestion(testData.getQuestion());
                correctTestData.setAnswer(testData.getAnswer());
                correctTestData.setTest(correctInnerTest);
                outputTestData.add(correctTestData);
            }
            else
            {
                String question = testData.getQuestion();
                int answer = testData.getAnswer();

                String[] parts = question.split(" ");
                int num1 = Integer.parseInt(parts[0]);
                String operator = parts[1];
                int num2 = Integer.parseInt(parts[2]);

                int correctAnswer = num1+num2;

                TestData correctTestData = new TestData();
                correctTestData.setQuestion(question);
                correctTestData.setAnswer(correctAnswer);
                outputTestData.add(correctTestData);
            }
        }
        outputData.setTestData(outputTestData);

        PayloadRequest payload = new PayloadRequest("JSON", "cb998dce-22dc-4768-82af-fb097703a5d0", outputData);

        // Set headers if necessary
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // Create HttpEntity with payload and headers
        HttpEntity<PayloadRequest> requestEntity = new HttpEntity<>(payload, headers);

        // Send POST request
        String a = restTemplate.exchange("https://centrala.ag3nts.org/report ", HttpMethod.POST, requestEntity, String.class).getBody();

        System.out.println("ok");
    }
}

