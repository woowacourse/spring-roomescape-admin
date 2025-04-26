package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.command.CreateReservationTimeCommand;
import roomescape.service.dto.query.ReservationTimeQuery;

@Service
@RequiredArgsConstructor
public class AdminReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeQuery create(CreateReservationTimeCommand command) {
        ReservationTime reservationTime = reservationTimeRepository.save(command.toReservationTime());
        return ReservationTimeQuery.from(reservationTime);
    }

    public List<ReservationTimeQuery> getAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.getAll();
        return reservationTimes.stream()
                .map(ReservationTimeQuery::from)
                .toList();
    }

    public void delete(Long id) {
        ReservationTime reservation = getReservation(id);
        reservationTimeRepository.remove(reservation);
    }

    private ReservationTime getReservation(Long reservationId) {
        return reservationTimeRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 시간이 존재하지 않습니다."));
    }
}
