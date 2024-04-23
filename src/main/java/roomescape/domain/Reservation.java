package roomescape.domain;

public class Reservation {
    private static final long NO_ID = 0;

    private final long id;
    private final Name name;
    private final Schedule schedule;

    public Reservation(long id, Name name, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.schedule = schedule;
    }

    public Reservation(long id, Reservation reservation) {
        this(id, reservation.name, reservation.schedule);
    }

    public Reservation(long id, String name, String date, ReservationTime reservationTime) {
        this(id, new Name(name), new Schedule(date, reservationTime));
    }

    public Reservation(String name, String date, ReservationTime reservationTime) {
        this(NO_ID, new Name(name), new Schedule(date, reservationTime));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getDate() {
        return schedule.getDate();
    }

    public String getTime() {
        return schedule.getTime();
    }

    public ReservationTime getReservationTime() {
        return schedule.getReservationTime();
    }
}
