package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Theme;
import roomescape.exception.BadRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;
import roomescape.service.dto.ThemeRequest;
import roomescape.service.dto.ThemeResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ThemeResponse create(ThemeRequest themeRequest) {
        Theme theme = themeRequest.toTheme();
        Theme saved = themeRepository.save(theme);

        return new ThemeResponse(saved);
    }

    public ThemeResponse findOne(long id) {
        Theme found = themeRepository.findById(id).orElseThrow();

        return new ThemeResponse(found);
    }

    public List<ThemeResponse> findAll() {
        return themeRepository.findAll().stream()
                .map(ThemeResponse::new)
                .toList();
    }

    public List<ThemeResponse> findPopularThemes(LocalDate startDay, LocalDate endDay, int limit) {
        return themeRepository.findPopularThemes(startDay, endDay, PageRequest.of(0, limit))
                .stream()
                .map(ThemeResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        if (reservationRepository.existsByThemeId(id)) {
            throw new BadRequestException("예약이 존재하는 테마는 삭제할 수 없습니다.");
        }
        themeRepository.deleteById(id);
    }
}
