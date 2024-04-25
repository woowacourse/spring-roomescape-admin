package roomescape.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDAO;

class FakeReservationTimeDAO implements ReservationTimeDAO {

    private List<ReservationTime> reservationTimes = new ArrayList<>(List.of(
            new ReservationTime(1, LocalTime.of(10, 0)),
            new ReservationTime(2, LocalTime.of(11, 0))
    ));


    @Override
    public List<ReservationTime> selectAllReservationTimes() {
        return reservationTimes;
    }

    @Override
    public ReservationTime selectReservationById(long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디가 없습니다."));
    }

    @Override
    public ReservationTime insertReservationTime(ReservationTime reservationTime) {
        reservationTimes.add(reservationTime);
        reservationTime.setId(3);
        return reservationTime;
    }

    @Override
    public void deleteReservationTime(long id) {
        ReservationTime findReservationTime = reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당하는 아이디가 없습니다."));

        reservationTimes.remove(findReservationTime);
    }
}
