package roomescape.reservation;

import java.time.LocalDate;
import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeService;

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    @Override
    public Reservation toReservation(ReservationRequest wantToSaveReservationRequest) {
        String name = wantToSaveReservationRequest.getName();
        LocalDate date = wantToSaveReservationRequest.getDate();
        Long timeId = wantToSaveReservationRequest.getTimeId();

        ReservationTime request = reservationTimeService.findById(timeId);

        return new Reservation(name, date, request);
    }

    @Override
    public Reservation saveReservation(ReservationRequest wantToSaveReservationRequest) {
        String name = wantToSaveReservationRequest.getName();
        LocalDate date = wantToSaveReservationRequest.getDate();
        Long timeId = wantToSaveReservationRequest.getTimeId();

        ReservationTime request = reservationTimeService.findById(timeId);

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
    public void validateSaveReservationAvailability(ReservationRequest wantToSaveReservationRequest) {
        if (reservationRepository.isExistReservationByDateAndTIme(wantToSaveReservationRequest)) {
            throw new IllegalArgumentException("[ERROR] 이미 예약되었어요. 다른 날짜를 골라주세요.");
        }
    }

    @Override
    public void validateDeleteReservationAvailability(Long wantToDeleteReservationId) {
        if (!reservationRepository.isExistReservationById(wantToDeleteReservationId)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 예약이에요. 확인해 주세요.");
        }
    }
}
