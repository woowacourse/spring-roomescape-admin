package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.GameTime;
import roomescape.repository.GameTimeRepository;

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
        return gameTimeRepository.save(gameTime);
    }

    public void deleteById(long id) {
        gameTimeRepository.deleteById(id);
    }
}
