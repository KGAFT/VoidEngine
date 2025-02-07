package com.kgaft.VoidEngine.Logging;

public class LoggerMessage {
    private String type = "";
    private String severity = "";
    private String message;
    private boolean isError = false;
    private String source;
    public LoggerMessage(String message, String source){
        this.message = message;
        this.source = source;
    }
    public LoggerMessage(String message, String source, boolean isError){
        this.message = message;
        this.isError = isError;
        this.source = source;
    }

    public LoggerMessage(String type, String source, String severity, String message, boolean isError) {
        this.type = type;
        this.severity = severity;
        this.message = message;
        this.isError = isError;
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
