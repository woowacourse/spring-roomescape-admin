package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.util.FormatUtils;

public record ReservationResponse(Long id, String name, String date, String time) {

    public static ReservationResponse toDto(final Reservation reservation) {

         return new ReservationResponse(
                 reservation.getId(),
                 reservation.getCustomerName(),
                 reservation.getReservationDateTime().toLocalDate().format(FormatUtils.DATE_FORMATTER),
                 reservation.getReservationDateTime().toLocalTime().format(FormatUtils.TIME_FORMATTER)
         );
    }
}
