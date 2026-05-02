package roomescape.domain;

import java.time.LocalDate;
import roomescape.controller.ReservationRequest;

public record Reservation(Long id, String name, LocalDate date, Long reservationTimeId) {

    public Reservation {
        validName();
        validDate();
        validTimeId();
    }

    public static Reservation transientOf(ReservationRequest reservationRequest) {
        return new Reservation(null, reservationRequest.name(), reservationRequest.date(), reservationRequest.timeId());
    }

    private void validName() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 필수이며 비어있을 수 없습니다.");
        }
    }

    private void validDate() {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 필수입니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("지나간 날짜로는 예약할 수 없습니다.");
        }
    }

    private void validTimeId() {
        if (reservationTimeId == null || reservationTimeId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 예약 시간대 번호입니다.");
        }
    }
}
