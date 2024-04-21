package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {

    private static final ReservationRepository INSTANCE = new ReservationRepository();

    private final List<Reservation> reservations = new ArrayList<>();
    private final static AtomicLong index = new AtomicLong(1);

    private ReservationRepository() {
    }

    public static ReservationRepository getInstance() {
        return INSTANCE;
    }

    public Long save(Reservation reservation) {
        Long reservationId = index.getAndIncrement();
        Reservation reservationToSave = new Reservation(reservationId, reservation);
        reservations.add(reservationToSave);

        return reservationId;
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[ERROR] id와 일치하는 예약 정보가 없습니다 : " + id));
    }

    public void delete(Long id) {
        Reservation reservationToDelete = findById(id);
        reservations.remove(reservationToDelete);
    }
}
