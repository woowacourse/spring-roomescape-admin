package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@DisplayName("시간 레포지토리")
@JdbcTest
@Import(TimeRepository.class)
class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @TestFactory
    Stream<DynamicTest> timeRepositoryDynamicTests() {
        return Stream.of(
                DynamicTest.dynamicTest("예약 정보를 저장한다.", () -> {
                    // given
                    TimeRequest timeRequest = new TimeRequest("10:00");

                    // when
                    Long id = timeRepository.save(timeRequest);

                    // then
                    assertThat(id).isEqualTo(1L);
                }),
                DynamicTest.dynamicTest("id로 시간 정보를 조회한다.", () -> {
                    // given
                    Optional<Time> reservation = timeRepository.findById(1L);

                    // then
                    assertAll(
                            () -> assertThat(reservation.get().getId()).isEqualTo(1L),
                            () -> assertThat(reservation.get().getStartAt()).isEqualTo("10:00")
                    );
                }),
                DynamicTest.dynamicTest("모든 시간 정보를 조회한다.", () -> {
                    // given & when
                    List<Time> reservations = timeRepository.findAll();

                    // then
                    assertThat(reservations).hasSize(1);
                }),
                DynamicTest.dynamicTest("id로 시간 정보를 제거한다.", () -> {
                    // given
                    Long id = 1L;

                    // when
                    timeRepository.deleteById(id);

                    // then
                    assertThat(timeRepository.findById(id).isEmpty()).isTrue();
                })
        );
    }
}
