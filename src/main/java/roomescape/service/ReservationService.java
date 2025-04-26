package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationRepository;
    private final ReservationTimeDao reservationTimeRepository;

    public ReservationService(ReservationDao reservationRepository, ReservationTimeDao reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("예약 시간이 존재하지 않습니다."));

        Reservation reservation = new Reservation(null, request.name(), request.date(), reservationTime);
        Reservation saved = reservationRepository.save(reservation);
        return new ReservationResponse(
                saved.getId(),
                saved.getName(),
                saved.getDate(),
                new ReservationTimeResponse(
                        saved.getTime().getId(),
                        saved.getTime().getStartAt()
                )
        );
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(saved -> new ReservationResponse(
                        saved.getId(),
                        saved.getName(),
                        saved.getDate(),
                        new ReservationTimeResponse(
                                saved.getTime().getId(),
                                saved.getTime().getStartAt()
                        )
                ))
                .toList();
    }

    public ReservationResponse getReservation(Long id) {
        Reservation saved = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다."));
        return new ReservationResponse(
                saved.getId(),
                saved.getName(),
                saved.getDate(),
                new ReservationTimeResponse(
                        saved.getTime().getId(),
                        saved.getTime().getStartAt()
                )
        );
    }

    public void deleteReservation(Long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다."));
        reservationRepository.delete(id);
    }
}
