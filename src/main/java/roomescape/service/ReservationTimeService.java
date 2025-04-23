package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeRegisterDto;
import roomescape.service.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Long saveReservationTime(final ReservationTimeRegisterDto reservationTimeRegisterDto) {
        ReservationTime reservationTime = reservationTimeRegisterDto.toReservationTime();
        long savedId = reservationTimeRepository.save(reservationTime);
        reservationTime.setId(savedId);

        return savedId;
    }

    public ReservationTime findReservationTimeById(final long id) {
        Optional<ReservationTime> foundReservationTime = reservationTimeRepository.findById(id);

        if (foundReservationTime.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id 와 일치하는 예약 시각이 존재하지 않습니다.");
        }
        return foundReservationTime.get();
    }

    public List<ReservationTimeResponseDto> findAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    public void deleteReservationTimeById(final long id) {
        findReservationTimeById(id);
        reservationTimeRepository.deleteById(id);
    }

}
