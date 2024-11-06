package spring.ai.s01e03;


public class PayloadRequest {
    private String task;
    private String apikey;
    private CalibrationData answer;

    public PayloadRequest(String task, String apikey, CalibrationData answer) {
        this.task = task;
        this.apikey = apikey;
        this.answer = answer;
    }

    // Getters and setters

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public CalibrationData getAnswer() {
        return answer;
    }

    public void setAnswer(CalibrationData answer) {
        this.answer = answer;
    }
}

