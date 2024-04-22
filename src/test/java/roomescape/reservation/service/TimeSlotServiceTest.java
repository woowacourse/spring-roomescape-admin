package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.TimeSlot;
import roomescape.reservation.dto.TimeSlotRequest;
import roomescape.reservation.dto.TimeSlotResponse;
import roomescape.reservation.repository.FakeTimeSlotDao;
import roomescape.reservation.repository.TimeSlotRepository;

class TimeSlotServiceTest {

    TimeSlotRepository timeSlotRepository;

    TimeSlotService timeSlotService;

    @BeforeEach
    void setUp() {
        timeSlotRepository = new FakeTimeSlotDao();
        timeSlotService = new TimeSlotService(timeSlotRepository);
    }

    @DisplayName("예약 시간 생성에 성공한다.")
    @Test
    void create() {
        //given
        LocalTime localTime = LocalTime.MIDNIGHT;
        TimeSlotRequest timeSlotRequest = new TimeSlotRequest(localTime);

        //when
        TimeSlotResponse timeSlotResponse = timeSlotService.create(timeSlotRequest);

        //then
        assertThat(timeSlotResponse.startAt()).isEqualTo(localTime);
        assertThat(timeSlotRepository.findAll()).hasSize(1);
    }

    @DisplayName("예약 시간 조회에 성공한다.")
    @Test
    void findAll() {
        //given
        long id = 1L;
        LocalTime localTime = LocalTime.MIDNIGHT;
        timeSlotRepository.save(new TimeSlot(id, localTime));

        //when
        List<TimeSlotResponse> timeSlots = timeSlotService.findAll();

        //then
        assertThat(timeSlots).hasSize(1);
    }
}
