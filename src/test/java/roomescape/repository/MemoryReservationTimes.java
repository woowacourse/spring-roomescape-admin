package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public class MemoryReservationTimes implements ReservationTimes {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationTime> findReservationTimes() {
        return reservationTimes;
    }

    @Override
    public Optional<ReservationTime> findReservationTimeById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.isIdOf(id))
                .findFirst();
    }

    @Override
    public ReservationTime createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        Long createdReservationId = index.incrementAndGet();
        ReservationTime createdReservationTime = new ReservationTime(
                createdReservationId,
                CustomDateTimeFormatter.getLocalTime(reservationTimeCreateRequest.startAt())
        );
        reservationTimes.add(createdReservationTime);
        return createdReservationTime;
    }

    @Override
    public void deleteReservationTimeById(Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.isIdOf(id));
    }

    public void cleanUp() {
        reservationTimes.clear();
    }
}
