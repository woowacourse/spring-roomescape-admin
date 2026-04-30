package roomescape.step4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.step4.domain.Reservation;
import roomescape.step4.domain.ReservationTime;
import roomescape.step4.dto.ReservationRequest;
import roomescape.step4.repository.ReservationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation saveReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeService.findReservationTime(request.timeId());

        Reservation reservation = new Reservation(
                null,
                request.name(),
                request.date(),
                reservationTime
        );
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        int deleted = reservationRepository.deleteById(id);

        if (deleted == 0) {
            throw new NoSuchElementException("삭제할 예약이 존재하지 않습니다.");
        }
    }
}
