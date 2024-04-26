package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.reservation.ReservationCreateRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.respository.ReservationRepository;
import roomescape.respository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse add(ReservationCreateRequest request) {
        validateNotExistReservationTime(request.getTimeId());
        ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId());
        Reservation reservation = request.toDomain(reservationTime);
        Reservation result = reservationRepository.add(reservation);
        return ReservationResponse.from(result);
    }

    public void delete(Long id) {
        validateNull(id);
        validateNotExistReservation(id);
        reservationRepository.delete(id);
    }

    private void validateNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("예약 아이디는 비어있을 수 없습니다.");
        }
    }

    private void validateNotExistReservation(Long id) {
        if (!reservationRepository.exist(id)) {
            throw new IllegalArgumentException("해당 아이디를 가진 예약이 존재하지 않습니다.");
        }
    }

    private void validateNotExistReservationTime(Long id) {
        if (!reservationTimeRepository.exist(id)) {
            throw new IllegalArgumentException("예약 시간 아이디에 해당하는 예약 시간이 존재하지 않습니다.");
        }
    }
}
