package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationCreateRequest reservationCreateRequest) {
        return ReservationResponse.from(reservationRepository.save(reservationCreateRequest));
    }

    public void delete(Long id) {
        if (reservationRepository.deleteById(id) == 0) {
            throw new NoSuchElementException("삭제할 예약이 존재하지 않습니다");
        }
    }
}
