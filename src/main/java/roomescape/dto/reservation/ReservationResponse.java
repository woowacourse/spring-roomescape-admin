package roomescape.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.dto.reservationtime.ReservationTimeResponse;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    @JsonProperty("time")
    private final ReservationTimeResponse reservationTimeResponse;

    private ReservationResponse(Long id,
                                String name,
                                String date,
                                ReservationTimeResponse reservationTimeResponse) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTimeResponse = reservationTimeResponse;
    }

    public static ReservationResponse from(Reservation reservation) {
        ReservationDate reservationDate = reservation.getDate();
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName().getValue(),
                reservationDate.toStringDate(),
                ReservationTimeResponse.from(reservation.getReservationTime())
        );
    }

    public static ReservationResponse of(Long id, String name, String date, ReservationTimeResponse timeResponseDto) {
        return new ReservationResponse(id, name, date, timeResponseDto);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationResponse other = (ReservationResponse) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.date, other.date)
                && Objects.equals(this.reservationTimeResponse, other.reservationTimeResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, reservationTimeResponse);
    }

    @Override
    public String toString() {
        return "ReservationCreateResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTimeDto=" + reservationTimeResponse +
                '}';
    }
}
