package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.request.CreateReservationRequest;
import roomescape.service.response.ReservationResponse;
import roomescape.service.response.ReservationTimeResponse;

@Service
@Transactional
public class ReservationService {

    private final ReservationTimeRepository reservationTImeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationTimeRepository reservationTImeRepository,
                              ReservationRepository reservationRepository) {
        this.reservationTImeRepository = reservationTImeRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long create(CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTImeRepository.findById(createReservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        createReservationRequest.timeId() + "에 해당하는 reservation_time 튜플이 없습니다."));
        return reservationRepository.create(
                new Reservation(
                        createReservationRequest.name(),
                        createReservationRequest.date(),
                        reservationTime));
    }

    public void deleteById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::toReservationResponse)
                .toList();
    }

    public ReservationResponse findById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException(reservationId + "에 해당하는 reservation_time 튜플이 없습니다."));
        return toReservationResponse(reservation);
    }

    private ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(
                        reservation.getTime().id(),
                        reservation.getTime().startAt()));
    }
}
