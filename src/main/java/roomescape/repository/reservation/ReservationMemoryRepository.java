package roomescape.repository.reservation;

//@Repository
//public class ReservationMemoryRepository implements ReservationRepository {
//
//    private final AtomicLong id = new AtomicLong(0);
//    private final Map<Long, Reservation> reservations = new HashMap<>();
//
//    @Override
//    public Reservation add(Reservation reservation) {
//        long newId = id.incrementAndGet();
//        Reservation reservationWithId = Reservation.of(newId, reservation);
//        reservations.put(newId, reservationWithId);
//        return Reservation.of(newId, reservationWithId);
//    }
//
//    @Override
//    public void remove(Long id) {
//        reservations.remove(id);
//    }
//
//    @Override
//    public List<Reservation> findAll() {
//        return reservations.values()
//                .stream()
//                .toList();
//    }
//}
