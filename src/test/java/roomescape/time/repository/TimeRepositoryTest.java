package roomescape.time.repository;

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
import org.springframework.test.context.jdbc.Sql;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeSaveRequest;

@DisplayName("시간 레포지토리")
@JdbcTest
@Sql(scripts = "classpath:reservation-test.sql")
@Import(TimeRepository.class)
class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @TestFactory
    Stream<DynamicTest> timeRepositoryDynamicTests() {
        return Stream.of(
                DynamicTest.dynamicTest("예약 정보를 저장한다.", () -> {
                    // given
                    TimeSaveRequest timeSaveRequest = new TimeSaveRequest("10:00");

                    // when
                    Time time = timeRepository.save(timeSaveRequest);

                    // then
                    assertThat(time.getId()).isEqualTo(2L);
                }),
                DynamicTest.dynamicTest("id로 시간 정보를 조회한다.", () -> {
                    // given
                    Optional<Time> reservation = timeRepository.findById(2L);

                    // then
                    assertAll(
                            () -> assertThat(reservation.get().getId()).isEqualTo(2L),
                            () -> assertThat(reservation.get().getStartAt()).isEqualTo("10:00")
                    );
                }),
                DynamicTest.dynamicTest("모든 시간 정보를 조회한다.", () -> {
                    // given & when
                    List<Time> reservations = timeRepository.findAll();

                    // then
                    assertThat(reservations).hasSize(2);
                }),
                DynamicTest.dynamicTest("time 기본키를 참조하는 reservation이 있는지 조회한다.", () -> {
                    // given & when
                    Optional<Time> time1 = timeRepository.findBySameReferId(1L);
                    Optional<Time> time2 = timeRepository.findBySameReferId(2L);

                    // then
                    assertAll(
                            () -> assertThat(time1.isPresent()).isTrue(),
                            () -> assertThat(time2.isPresent()).isFalse()
                    );
                }),
                DynamicTest.dynamicTest("id로 시간 정보를 제거한다.", () -> {
                    // given
                    Long id = 2L;

                    // when
                    timeRepository.deleteById(id);

                    // then
                    assertThat(timeRepository.findById(id).isEmpty()).isTrue();
                })
        );
    }
}
