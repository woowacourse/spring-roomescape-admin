package roomescape.exception;

public enum DomainValidationMessage {
    PAST_DATE_TIME("과거의 날짜와 시간으로 예약을 생성할 수 없습니다.");

    private final String content;

    DomainValidationMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
