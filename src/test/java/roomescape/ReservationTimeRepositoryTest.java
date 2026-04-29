package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class ReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void init() {
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("존재하지 않는 id로 삭제 시 예외를 발생시킨다.")
    void delete_reservation_by_id_fail_when_reservation_not_found() {
        // when & then
        assertThatThrownBy(() -> reservationTimeRepository.deleteById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("[ERROR] 해당 id의 ReservationTime이 존재하지 않습니다.");
    }
}