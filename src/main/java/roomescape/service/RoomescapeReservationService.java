package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;

import java.util.List;

@Service
public class RoomescapeReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDao timeDao;

    public RoomescapeReservationService(ReservationDAO reservationDAO, TimeDao timeDao) {
        this.reservationDAO = reservationDAO;
        this.timeDao = timeDao;
    }

    public List<ReservationResponseDto> getAllReservation() {
        return reservationDAO.findAll()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto createReservation(ReservationRequestDto request) {
        ReservationTimeEntity timeEntity = timeDao.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));

        ReservationEntity newReservation = request.toEntity(timeEntity);
        List<ReservationEntity> reservations = reservationDAO.findAll();
        if (reservations.stream().anyMatch(reservation -> reservation.isDuplicatedWith(newReservation))) {
            throw new IllegalArgumentException("해당 날짜에는 이미 예약이 존재합니다.");
        }

        ReservationEntity saved = reservationDAO.save(newReservation);
        return ReservationResponseDto.from(saved);
    }

    public void deleteReservation(final Long id) {
        final int deleted = reservationDAO.deleteById(id);
        if (deleted == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
