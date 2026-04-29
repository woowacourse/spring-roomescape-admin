package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;
import roomescape.util.DateAndTimeConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = ReservationTime.create(
                null,
                DateAndTimeConverter.parseToLocalDateTime(reservationTimeRequest.startAt())
        );

        return new ReservationTimeResponse(
                reservationTimeRepository.save(reservationTime),
                reservationTimeRequest.startAt()
        );
    }

    public List<ReservationTimeResponse> getAll() {
        return reservationTimeRepository.getAll()
                .stream()
                .map(reservationTime -> new ReservationTimeResponse(
                        reservationTime.getId(),
                        DateAndTimeConverter.formatDateAndTime(reservationTime.getStartAt()))
                ).toList();
    }

    @Transactional
    public void delete(Long id){
        reservationTimeRepository.delete(id);
    }
}
