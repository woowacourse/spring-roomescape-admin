package roomescape.exception;

public enum ValidationExceptionMessage {

    NULL_OR_BLANK_NAME("[ERROR] 이름은 빈 값이나 공백값을 허용하지 않습니다."),
    NULL_DATE("[ERROR] 날짜는 빈 값을 허용하지 않습니다."),
    NULL_TIME("[ERROR] 시간은 빈 값을 허용하지 않습니다.");

    private final String content;

    ValidationExceptionMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
