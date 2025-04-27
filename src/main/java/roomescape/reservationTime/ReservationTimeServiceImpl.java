package roomescape.reservationTime;

import java.util.List;

public class ReservationTimeServiceImpl implements ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeServiceImpl(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public ReservationTime savaReservationTime(ReservationTime wantToSaveReservationTime) {
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

    @Override
    public ReservationTime findById(Long timeId) {
        return reservationTimeRepository.findById(timeId);
    }

    @Override
    public void validateSaveReservationTimeAvailability(ReservationTime wantToValidateReservationTimeId) {
        if (reservationTimeRepository.isExistTimeByStartTime(wantToValidateReservationTimeId)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 시간이에요. 다시 입력해 주세요.");
        }
    }

    @Override
    public void validateDeleteReservationTimeAvailability(Long wantToValidateReservationTimeId) {
        if (!reservationTimeRepository.isExistTimeById(wantToValidateReservationTimeId)) {
            throw new IllegalArgumentException("[ERROR] 삭제하고자 하는 시간이 없어요. 확인해 주세요.");
        }
    }

}
