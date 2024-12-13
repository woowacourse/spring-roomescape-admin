package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
import roomescape.model.Reservation;

@Controller
public class ReservationController {

    // 메모리에 예약 데이터 저장
    private List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        // 테스트를 위한 임의의 예약 데이터 추가
        reservations.add(new Reservation(1, "브라운", "2024-12-12", "10:00"));
        reservations.add(new Reservation(2, "솔라", "2024-12-12", "11:00"));
        reservations.add(new Reservation(2, "부리", "2024-12-13", "14:00"));
    }

    // 예약 관리 페이지 로드
    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy"; // reservation-legacy.html 페이지 반환
    }

    // 예약 목록 조회 API
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations; // 예약 목록 반환
    }
}

