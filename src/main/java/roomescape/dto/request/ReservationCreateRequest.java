package roomescape.dto.request;

import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationCreateRequest {
        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("이름은 필수값입니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수값입니다.");
        }
        if (timeId == null) {
            throw new IllegalArgumentException("시간은 필수값입니다.");
        }
    }
}
