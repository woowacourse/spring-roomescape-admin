package roomescape.domain;

import java.util.Objects;

public class ReservationName {
    private final String value;

    public ReservationName(final String value) {
        this.value = Objects.requireNonNull(value, "예약명은 null일 수 없습니다.");
        validateName(value);
    }

    public String getValue() {
        return value;
    }

    private void validateName(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("예약명은 공백일 수 없습니다.");
        }
        if (value.length() > 10) {
            throw new IllegalArgumentException("예약명은 10자 이내여야 합니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationName that)) {
            return false;
        }
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
