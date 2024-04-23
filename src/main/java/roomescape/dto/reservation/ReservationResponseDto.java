package roomescape.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import roomescape.domain.reservation.Date;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.reservationtime.ReservationTimeResponseDto;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final String date;
    @JsonProperty("time")
    private final ReservationTimeResponseDto reservationTimeResponseDto;

    private ReservationResponseDto(Long id,
                                   String name,
                                   String date,
                                   ReservationTimeResponseDto reservationTimeResponseDto) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTimeResponseDto = reservationTimeResponseDto;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        Date date = reservation.getDate();
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                date.toStringDate(),
                ReservationTimeResponseDto.from(reservation.getReservationTime())
        );
    }

    public static ReservationResponseDto of(Long id,
                                            String name,
                                            String date,
                                            ReservationTimeResponseDto timeResponseDto) {
        return new ReservationResponseDto(
                id,
                name,
                date,
                timeResponseDto
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

    public ReservationTimeResponseDto getReservationTimeDto() {
        return reservationTimeResponseDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationResponseDto other = (ReservationResponseDto) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.date, other.date)
                && Objects.equals(this.reservationTimeResponseDto, other.reservationTimeResponseDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, reservationTimeResponseDto);
    }

    @Override
    public String toString() {
        return "ReservationCreateResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTimeDto=" + reservationTimeResponseDto +
                '}';
    }
}
