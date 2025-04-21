package roomescape.reservation.domain;

public class ReserverName {

    private final String value;

    private ReserverName(final String value) {
        validate(value);
        this.value = value;
    }

    public static ReserverName from(final String name) {
        return new ReserverName(name);
    }

    private void validate(final String value) {
        validateBlank(value);
    }

    private void validateBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 빈 칸일 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
