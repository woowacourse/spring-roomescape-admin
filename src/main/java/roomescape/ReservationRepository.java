package roomescape;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {

    private static final ReservationRepository INSTANCE = new ReservationRepository();

    private final List<Reservation> reservations = new ArrayList<>();

    private ReservationRepository() {
    }

    public static ReservationRepository getInstance() {
        return INSTANCE;
    }

    public void save(Reservation reservation) {
        reservations.add(reservation);
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
