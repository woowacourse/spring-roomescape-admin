package roomescape.core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationRepository;
import roomescape.core.repository.ReservationTimeRepository;
import roomescape.core.service.dto.ReservationTimeServiceRequest;
import roomescape.core.service.dto.ReservationTimeServiceResponse;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository, ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationTimeServiceResponse create(ReservationTimeServiceRequest reservationTimeServiceRequest) {
        ReservationTime reservationTime = reservationTimeServiceRequest.toReservationTime();
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        return ReservationTimeServiceResponse.from(savedReservationTime);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeServiceResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeServiceResponse::from)
                .toList();
    }

    public void delete(Long id) {
        ReservationTime reservationTime = reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Time not found"));

        validateExistReservation(id);

        reservationTimeRepository.delete(reservationTime);
    }

    private void validateExistReservation(Long timeId) {
        boolean existByTimeId = reservationRepository.existByTimeId(timeId);

        if (existByTimeId) {
            throw new IllegalArgumentException("Reservation already exists in time_id = "
                    + timeId +  " , can't delete reservation time");
        }
    }
}
