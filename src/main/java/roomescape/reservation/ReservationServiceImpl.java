package roomescape.reservation;

import java.util.List;
import roomescape.dto.ReservationRequest;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation saveReservation(ReservationRequest wantToSaveReservation) {
        return reservationRepository.saveReservation(wantToSaveReservation);
    }

    @Override
    public void deleteReservation(Long wantToDeleteId) {
        reservationRepository.deleteReservation(wantToDeleteId);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    @Override
    public void validateReservationTimeAvailability(ReservationRequest wantToSaveReservationRequest) {
        if (reservationRepository.isExistReservation(wantToSaveReservationRequest)) {
            throw new IllegalArgumentException("[ERROR] 이미 예약되었어요. 다른 날짜를 골라주세요.");
        }
    }
}
