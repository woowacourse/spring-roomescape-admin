package roomescape.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

public class CollectionReservationRepository implements ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;
    private final CollectionReservationTimeRepository timeRepository;

    public CollectionReservationRepository(CollectionReservationTimeRepository timeRepository) {
        this(new ArrayList<>(), new AtomicLong(0), timeRepository);
    }

    public CollectionReservationRepository(List<Reservation> reservations, AtomicLong atomicLong,
                                           CollectionReservationTimeRepository timeRepository) {
        this.reservations = reservations;
        this.atomicLong = atomicLong;
        this.timeRepository = timeRepository;
    }

    public CollectionReservationRepository(List<Reservation> reservations,
                                           CollectionReservationTimeRepository timeRepository) {
        this(reservations, new AtomicLong(0), timeRepository);
    }

    @Override
    public Reservation save(ReservationRequest reservationRequest) {
        Reservation reservation = fromRequest(reservationRequest);
        reservations.add(reservation);
        return reservation;
    }

    private Reservation fromRequest(ReservationRequest reservationRequest) {
        long id = atomicLong.incrementAndGet();

        String name = reservationRequest.name();
        LocalDate date = reservationRequest.date();
        ReservationTime reservationTime = timeRepository.findAll().stream()
                .filter(sameId(reservationRequest))
                .findAny()
                .orElseThrow();
        return new Reservation(id, name, date, reservationTime);
    }

    private static Predicate<ReservationTime> sameId(ReservationRequest reservationRequest) {
        return reservationTime -> reservationTime.getId() == reservationRequest.timeId();
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.stream()
                .sorted()
                .toList();
    }

    @Override
    public void delete(long id) {
        reservations.stream()
                .filter(reservation -> reservation.hasSameId(id))
                .findAny()
                .ifPresent(reservations::remove);
    }
}
