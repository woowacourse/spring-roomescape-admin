package roomescape.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.mapper.ReservationMapper;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeService timeService;

    public ReservationService(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }

    public ReservationResponse registerReservation(ReservationRequest request) {
        validateDateFormat(request.date());

        ReservationTime reservationTime = timeService.getTimeById(request.timeId());
        Reservation reservation = ReservationMapper.toDomain(request, reservationTime);
        Long id = reservationRepository.save(reservation);

        return ReservationMapper.toDto(Reservation.withId(id, reservation));
    }

    private void validateDateFormat(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다: " + date);
        }
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationMapper.toDtos(reservations);
    }

    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
