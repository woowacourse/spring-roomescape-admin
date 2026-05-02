package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDTO;
import roomescape.dto.ReservationTimeResponseDTO;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDTO addReservationTime(
            ReservationTimeRequestDTO reservationTimeRequest) {
        ReservationTime reservationTime = new ReservationTime(null,
                LocalTime.parse(reservationTimeRequest.startAt()));

        ReservationTime savedTime = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponseDTO.from(savedTime);
    }

    public List<ReservationTimeResponseDTO> findAllReservationTime() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDTO::from).collect(Collectors.toList());
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.delete(id);
    }
}
