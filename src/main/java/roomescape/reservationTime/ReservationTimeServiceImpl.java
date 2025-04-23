package roomescape.reservationTime;

import java.util.List;

public class ReservationTimeServiceImpl implements ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeServiceImpl(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public ReservationTime savaReservation(ReservationTime wantToSaveReservationTime) {
        return reservationTimeRepository.saveReservationTime(wantToSaveReservationTime);
    }

    @Override
    public void deleteReservation(Long wantToDeleteId) {
        reservationTimeRepository.deleteReservationTime(wantToDeleteId);
    }

    @Override
    public List<ReservationTime> findAllReservationTime() {
        return reservationTimeRepository.findAllReservationTimes();
    }

}
