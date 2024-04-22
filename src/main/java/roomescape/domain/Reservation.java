package roomescape.domain;

public class Reservation { // TODO final 적용

    private Long id;
    private String name;
    private String date;
    private ReservationTimeDto reservationTimeDto;

    public Reservation() {}

    public Reservation(Long id, String name, String date, ReservationTimeDto reservationTimeDto) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTimeDto = reservationTimeDto;
    }

    public Reservation(Reservation reservation, Long id) {
        this(id, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
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

    public ReservationTimeDto getReservationTime() {
        return reservationTimeDto;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", reservationTime=" + reservationTimeDto +
                '}';
    }
}
