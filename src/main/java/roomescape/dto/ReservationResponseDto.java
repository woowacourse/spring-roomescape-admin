package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public class ReservationResponseDto {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Long id;
    private String name;
    private String date;
    private ReservationTimeResponseDto time;

    public ReservationResponseDto(Long id, String name, String date, ReservationTimeResponseDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(reservation.getTime());
        String date = reservation.getDate().format(DATE_FORMATTER);
        return new ReservationResponseDto(
                reservation.getId(), reservation.getName().getValue(), date, timeResponseDto
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeResponseDto getTime() {
        return time;
    }
}
