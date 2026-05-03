package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    public static final String TIME_SLOT_DOES_NOT_EXISTS = "조회된 타임 슬롯이 없습니다.";
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation reserve(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException(TIME_SLOT_DOES_NOT_EXISTS));

        Reservation reservation = Reservation.of(request.getName(), request.getDate(), reservationTime);

        return reservationRepository.save(reservation);
    }

    public void cancel(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
