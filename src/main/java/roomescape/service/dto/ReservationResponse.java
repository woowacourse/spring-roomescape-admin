package roomescape.service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        LocalDate date,
        MemberResponse member,
        ReservationTimeResponse time,
        ThemeResponse theme,
        String status
) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getSlot().getDate(),
                new MemberResponse(reservation.getMember()),
                new ReservationTimeResponse(reservation.getSlot().getTime()),
                new ThemeResponse(reservation.getSlot().getTheme()),
                reservation.getStatus().getViewName()
        );
    }
}
