package roomescape.reservation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserverName {

    private final String value;

    public static ReserverName from(final String name) {
        validate(name);
        return new ReserverName(name);
    }

    private static void validate(final String value) {
        validateBlank(value);
    }

    private static void validateBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 빈 칸일 수 없습니다.");
        }
    }
}
