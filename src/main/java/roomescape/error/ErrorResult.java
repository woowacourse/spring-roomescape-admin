package roomescape.error;

public class ErrorResult {
    private String code;
    private String message;

    public ErrorResult() {
    }

    public ErrorResult(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
