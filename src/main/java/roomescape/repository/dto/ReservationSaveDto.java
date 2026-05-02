package roomescape.repository.dto;

import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationCreateDto;

public class ReservationSaveDto {
    private final String name;
    private final String date;
    private final ReservationTime reservationTime;

    public ReservationSaveDto(String name, String date, ReservationTime reservationTime) {
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public static ReservationSaveDto toDto(ReservationCreateDto dto, ReservationTime reservationTime) {
        return new ReservationSaveDto(dto.getName(), dto.getDate(), reservationTime);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
