package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.GameTime;
import roomescape.repository.GameTimeRepository;
import roomescape.service.exception.DataAlreadyExistException;

@Service
public class GameTimeService {
    private final GameTimeRepository gameTimeRepository;

    public GameTimeService(GameTimeRepository gameTimeRepository) {
        this.gameTimeRepository = gameTimeRepository;
    }

    public List<GameTime> readAll() {
        return gameTimeRepository.readAll();
    }

    public GameTime findById(long id) {
        return gameTimeRepository.findById(id);
    }

    public GameTime save(GameTime gameTime) {
        if (gameTimeRepository.existByStartAt(gameTime)) {
            throw new DataAlreadyExistException("해당 게임 시간은 이미 등록되어 있습니다: " + gameTime.getStartAt());
        }
        return gameTimeRepository.save(gameTime);
    }

    public void deleteById(long id) {
        gameTimeRepository.deleteById(id);
    }
}
