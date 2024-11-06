package spring.ai.s01e03;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class CalibrationData {

    private String apikey;
    private String description;
    private String copyright;

    @JsonProperty("test-data")
    private List<TestData> testData;

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setTestData(List<TestData> testData) {
        this.testData = testData;
    }

    public String getApikey() {
        return apikey;
    }

    public String getDescription() {
        return description;
    }

    public String getCopyright() {
        return copyright;
    }

    public List<TestData> getTestData() {
        return testData;
    }
}