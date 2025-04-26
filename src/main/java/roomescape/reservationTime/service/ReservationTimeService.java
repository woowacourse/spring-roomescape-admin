package roomescape.reservationTime.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.globalException.CustomException;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.domain.dto.ReservationTimeReqDto;
import roomescape.reservationTime.domain.dto.ReservationTimeResDto;
import roomescape.reservationTime.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public List<ReservationTimeResDto> readAll() {
        List<ReservationTime> reservationTimes = repository.findAll();
        return reservationTimes.stream()
                .map(this::convertToReservationTimeResDto)
                .toList();
    }

    public ReservationTimeResDto add(ReservationTimeReqDto dto) {
        ReservationTime reservationTime = convertToReservationTimeReqDto(dto);
        validateDuplicateTime(reservationTime);
        ReservationTime savedReservationTime = repository.add(reservationTime);
        return convertToReservationTimeResDto(savedReservationTime);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private void validateDuplicateTime(ReservationTime inputReservationTime) {
        List<ReservationTime> reservationTimes = repository.findAll();
        for (ReservationTime reservationTime : reservationTimes) {
            if (inputReservationTime.isSameTime(reservationTime)) {
                throw new CustomException(HttpStatus.CONFLICT, "이미 등록되어 있는 시간입니다.");
            }
        }
    }

    public ReservationTimeResDto convertToReservationTimeResDto(ReservationTime reservationTime) {
        return new ReservationTimeResDto(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    private ReservationTime convertToReservationTimeReqDto(ReservationTimeReqDto dto) {
        return ReservationTime.from(dto);
    }
}
