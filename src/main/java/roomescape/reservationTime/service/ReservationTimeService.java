package roomescape.reservationTime.service;

import org.springframework.stereotype.Service;
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
        ReservationTime reservationTime = convertToReservationTime(dto);
        ReservationTime savedReservationTime = repository.add(reservationTime);
        return convertToReservationTimeResDto(savedReservationTime);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private ReservationTimeResDto convertToReservationTimeResDto(ReservationTime reservationTime) {
        return new ReservationTimeResDto(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }

    private ReservationTime convertToReservationTime(ReservationTimeReqDto dto) {
        return new ReservationTime(
                dto.startAt()
        );
    }
}
