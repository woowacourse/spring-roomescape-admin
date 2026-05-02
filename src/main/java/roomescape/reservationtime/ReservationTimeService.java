package roomescape.reservationtime;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDTO createReservationTime(ReservationTimeRequestDTO reservationTimeRequestDTO) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDTO.getStartAt());
        Long id = reservationTimeRepository.insert(reservationTime);
        return new ReservationTimeResponseDTO(
                id,
                reservationTime.getStartAt());
    }

    public List<ReservationTimeResponseDTO> findReservationTimes() {
        return reservationTimeRepository.findAllReservationTimes().stream()
                .map(reservationTime -> new ReservationTimeResponseDTO(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                )).toList();
    }

    public void deleteReservationTime(long id) {
        reservationTimeRepository.delete(id);
    }
}
