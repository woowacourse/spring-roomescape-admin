package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.exception.IllegalReservationTimeException;
import roomescape.repository.GameTimeRepository;

@Service
public class GameTimeService {
    private final GameTimeRepository gameTimeRepository;

    public GameTimeService(GameTimeRepository gameTimeRepository) {
        this.gameTimeRepository = gameTimeRepository;
    }

    public List<ReservationTime> readAll() {
        return gameTimeRepository.readAll();
    }

    public ReservationTime findById(long id) {
        return gameTimeRepository.findById(id);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        if (gameTimeRepository.existByStartAt(reservationTime)) {
            throw new IllegalReservationTimeException("해당 게임 시간은 이미 등록되어 있습니다: " + reservationTime.getStartAt());
        }
        return gameTimeRepository.save(reservationTime);
    }

    public void deleteById(long id) {
        gameTimeRepository.deleteById(id);
    }
}
