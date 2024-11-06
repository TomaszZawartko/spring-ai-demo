package spring.ai.s01e03;

public class TestData {
    private String question;
    private int answer;
    private InnerTest test;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setTest(InnerTest test) {
        this.test = test;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public InnerTest getTest() {
        return test;
    }
}
