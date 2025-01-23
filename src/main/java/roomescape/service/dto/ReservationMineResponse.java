package roomescape.service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationMineResponse(
        long id,
        String themeName,
        LocalDate date,
        String startAt,
        String status
) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationMineResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getSlot().getTheme().getName(),
                reservation.getSlot().getDate(),
                reservation.getSlot().getTime().getStartAt().format(FORMATTER),
                //TODO: 예약 성공의 경우 0, 예약 대기의 경우 1 이상의 숫자를 넘겨주면, 프론트 단에서 이를 처리하도록 변경하기
                reservation.getStatus().getViewName()
        );
    }
}
