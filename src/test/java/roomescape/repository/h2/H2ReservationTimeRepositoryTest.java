package roomescape.repository.h2;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.TestFixture.createTime;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@JdbcTest
@Import(H2ReservationTimeRepository.class)
class H2ReservationTimeRepositoryTest {
    @Autowired
    private ReservationTimeRepository timeRepository;

    @Test
    @DisplayName("예약시간 목록을 조회하면 저장한 객체와 동등하다.")
    void findAll() {
        assertThat(timeRepository.findAll()).isEmpty();

        List<ReservationTime> savedTimes = new ArrayList<>();
        savedTimes.add(timeRepository.save(createTime()));
        savedTimes.add(timeRepository.save(createTime()));
        savedTimes.add(timeRepository.save(createTime()));

        assertThat(timeRepository.findAll())
                .hasSize(savedTimes.size())
                .containsAll(savedTimes);
    }

    @Test
    @DisplayName("timeId 예약시간을 조회한다.")
    void findById() {
        ReservationTime saved = timeRepository.save(createTime());
        assertThat(timeRepository.findById(saved.getId()))
                .isEqualTo(saved);
    }

    @Test
    @DisplayName("예약시간을 저장하면 id는 null이 아니다.")
    void save() {
        assertThat(timeRepository.save(createTime()).getId()).isNotNull();
    }

    @Test
    @DisplayName("저장된 예약시간을 삭제하면, 존재하지 않는다.")
    void delete() {
        ReservationTime saved = timeRepository.save(createTime());
        timeRepository.delete(saved.getId());

        assertThat(timeRepository.isExists(saved.getId())).isFalse();
    }

    @Test
    @DisplayName("timeId에 해당하는 예약시간이 존재하는지 확인한다.")
    void isExists() {
        assertThat(timeRepository.isExists(timeRepository.save(createTime()).getId())).isTrue();
    }
}
