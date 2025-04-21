package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.repository.ReservationTimeSlotFakeRepository;

class ReservationAdminControllerTest {

    /*

    DTO를 사용하면 요청/응답 형태가 변할 때 DTO, 컨트롤러

    1. Time 추가의 요청 형태는 충분히 변할 수 있다.
    인터페이스를 바꾸는 것보다 DTO의 필드를 바꾸는 게 변경에 대한 영향을 덜 받는다.
    하지만 "요청"에 대한 관리 포인트가 2개 (DTO, 컨트롤러)
    vs
    인터페이스를 바꾸면 컴파일 에러가

    2. Reservation Controller처럼 이 친구도 상태코드를 동적으로 내줄 가능성이 있다.
    따라서 ResponseEntity를 반환한다.

    3. Time 추가의 응답 형태는 충분히 변할 수 있다.
    구체적인 응답 형태로부터의 영향을 덜 받기 위해 응답 DTO를 사용하며 상태 코드만 검증한다.
     */
    @Test
    void createTimeSlot() {
        //given
        final var controller = new ReservationTimeSlotController(new ReservationTimeSlotFakeRepository());

        //when
        final var responseEntity = controller.create(new CreateTimeSlotRequest(LocalTime.of(10, 0)));
        
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
