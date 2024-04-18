package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.ReservationDto;

class ReservationRepositoryTest {

    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
        reservationRepository.add(ReservationDto.of(null, "다온", "2024-04-18", "16:12"));
        reservationRepository.add(ReservationDto.of(null, "아루", "2024-04-15", "09:15"));
    }

    @Test
    @DisplayName("모든 예약 정보를 가져온다.")
    void findAll() {
        List<ReservationDto> result = reservationRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("에약 정보를 추가한다.")
    void add() {
        String name = "브라운";
        String date = "2023-08-05";
        String time = "15:40";
        ReservationDto reservationDto = ReservationDto.of(null, name, date, time);

        ReservationDto result = reservationRepository.add(reservationDto);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(3L),
                () -> assertThat(reservationRepository.findAll()).hasSize(3)
        );
    }

    @Test
    @DisplayName("에약 정보를 삭제한다.")
    void delete() {
        Long id = 1L;

        reservationRepository.delete(id);

        assertThat(reservationRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제시 경로 변수가 null이면 예외가 발생한다.")
    void deleteNullId() {
        Long id = null;

        assertThatThrownBy(() -> reservationRepository.delete(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약을 삭제시 id에 해당하는 예약이 없다면 예외가 발생한다.")
    void deleteNotContainsId() {
        Long id = 3L;

        assertThatThrownBy(() -> reservationRepository.delete(id))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 예약 정보를 삭제한다.")
    void deleteAll() {
        reservationRepository.deleteAll();

        assertThat(reservationRepository.findAll()).hasSize(0);
    }
}
