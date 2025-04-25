package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.exceptions.EntityNotFoundException;

@Service
@Transactional
public class ReservationTimeService {

    private final ReservationTimeDao repository;

    public ReservationTimeService(ReservationTimeDao repository) {
        this.repository = repository;
    }

    public void existsTimeById(long id) {
        if (!repository.existsTimeById(id)) {
            throw new EntityNotFoundException("해당 예약 시간이 존재하지 않습니다: " + id);
        }
    }

    public List<ReservationTimeResponse> readReservationTime() {
        return repository.findAll().stream()
                .map(ReservationTimeResponse::toDto)
                .toList();
    }

    public ReservationTimeResponse postReservationTime(ReservationTimeRequest request) {
        ReservationTime newReservation = repository.save(request.toEntity());
        return ReservationTimeResponse.toDto(newReservation);
    }

    public void deleteReservationTime(long id) {
        repository.deleteById(id);
    }
}
