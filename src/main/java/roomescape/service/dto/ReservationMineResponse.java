package roomescape.service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationStatus;

public record ReservationMineResponse(
        long reservationId,
        String themeName,
        LocalDate date,
        String startAt,
        String status
) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationMineResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getTheme().getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt().format(FORMATTER),
                //TODO: reservation에서 가져오는 방식으로 변경하기
                ReservationStatus.RESERVED.getViewName()
        );
    }
}
