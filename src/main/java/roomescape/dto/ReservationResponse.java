package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.util.FormatUtils;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse toDto(final Reservation reservation) {

         return new ReservationResponse(
                 reservation.getId(),
                 reservation.getCustomerName(),
                 reservation.getReservationDate().format(FormatUtils.DATE_FORMATTER),
                 reservation.getReservationTime().getStartAt().format(FormatUtils.TIME_FORMATTER)
         );
    }
}
