package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());

    //private AtomicLong index;

//    public ReservationRepository() {
//        List<Reservation> initialReservations = List.of(
//                Reservation.createReservation(1L, "브라운", LocalDateTime.of(2024, 4, 1, 10, 0)),
//                Reservation.createReservation(2L, "솔라", LocalDateTime.of(2024, 4, 1, 11, 0)),
//                Reservation.createReservation(3L, "브리", LocalDateTime.of(2024, 4, 2, 14, 0))
//        );
//        reservations.addAll(initialReservations);
//
//        long size = reservations.size();
//
//        this.index = new AtomicLong(size);
//    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        LocalDate date = LocalDate.parse(rs.getString("reservation_date"));
        LocalTime time = LocalTime.parse(rs.getString("reservation_time"));
        return Reservation.from(
                rs.getLong("id"),
                rs.getString("name"),
                date,
                time
        );
    };

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, reservation_date, reservation_time FROM Reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

//    public Long add(Reservation reservation) {
//        long currentIndex = index.incrementAndGet();
//        Reservation newReservation = Reservation.createReservationWithId(currentIndex, reservation);
//        reservations.add(newReservation);
//        return currentIndex;
//    }
//
//    public Reservation findById(Long id) {
//        return reservations.stream()
//                .filter(reservation -> reservation.sameId(id))
//                .findAny()
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 존재하지 않습니다."));
//    }
//
//    public void deleteById(Long id) {
//        Reservation reservation = findById(id);
//        reservations.remove(reservation);
//    }

}
