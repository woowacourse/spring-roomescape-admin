package roomescape.reservation;

import java.time.LocalDate;
import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeRepository;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public Reservation saveReservation(ReservationRequest wantToSaveReservationRequest) {
        String name = wantToSaveReservationRequest.getName();
        LocalDate date = wantToSaveReservationRequest.getDate();
        Long timeId = wantToSaveReservationRequest.getTimeId();

        ReservationTime request = reservationTimeRepository.findById(timeId);

        Reservation wantToSaveReservation = new Reservation(
                name, date, request
        );

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
