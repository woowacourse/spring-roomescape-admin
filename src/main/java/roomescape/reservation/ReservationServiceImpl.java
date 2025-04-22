package roomescape.reservation;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation saveReservation(Reservation wantToSaveReservation) {
        return reservationRepository.saveReservation(wantToSaveReservation);
    }

    @Override
    public void deleteReservation(Long wantToDeleteId) {
        reservationRepository.deleteReservation(wantToDeleteId);
    }

    @Override
    public Reservation findReservationById(Long wandToFindId) {
        return reservationRepository.findReservationById(wandToFindId);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    @Override
    public void validateReservationTimeAvailability(Reservation wantToSaveReservation) {
        if (reservationRepository.isExistReservation(wantToSaveReservation)) {
            throw new IllegalArgumentException("[ERROR] 이미 예약되었어요. 다른 날짜를 골라주세요.");
        }
    }
}
