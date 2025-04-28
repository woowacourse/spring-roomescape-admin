package roomescape.presentation.dto.request;

import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Objects;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationCreateRequest {
        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("이름은 필수값입니다.");
        }
        Objects.requireNonNull(date, "날짜는 필수값입니다.");
        Objects.requireNonNull(timeId, "시간은 필수값입니다.");
    }
}
