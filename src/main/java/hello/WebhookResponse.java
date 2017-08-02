package hello;

public class WebhookResponse {
    private final String displayText;
    private final String speech;

    private final String source = "java-webhook";

    public WebhookResponse(String displayText, String speech) {
        this.displayText = displayText;
        this.speech = speech;
    }


    public String getDisplayText() {
        return displayText;
    }
    
    public String getSpeech() {
        return speech;
    }

    public String getSource() {
        return source;
    }
}
