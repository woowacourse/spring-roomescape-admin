package roomescape.consoleview;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.controller.dto.FindReservationResponse;
import roomescape.controller.dto.FindReservationTimeResponse;

@Component
public class OutputView {

    public void printHelp() {
        System.out.println("""
            [도움말 명령어]
            help: 모든 명령어 목록을 조회합니다.
                        
            [조회 명령어]
            show reservation: 전체 예약을 조회합니다.
            show time: 전체 시간을 조회합니다.
                        
            [생성 명령어]
            create reservation 이름 날짜 시간번호: 예약을 생성합니다.
            - 예시) create reservation John 2025-01-01 1
            create time 시간: 예약 가능한 시간을 추가합니다.
            - 예시) create time 23:00
                        
            [삭제 명령어]
            delete reservation 예약번호: 예약을 삭제합니다.
            delete time 시간번호: 시간을 삭제합니다.
            """);
    }

    public void printReservations(List<FindReservationResponse> reservations) {
        StringBuilder sb = new StringBuilder();
        if (reservations.isEmpty()) {
            sb.append("예약이 존재하지 않습니다.");
        }
        for (FindReservationResponse reservation : reservations) {
            sb.append("\n---------------------");
            sb.append("\n예약번호: ").append(reservation.id());
            sb.append("\n예약자: ").append(reservation.name());
            sb.append("\n날짜: ").append(reservation.date());
            sb.append("\n시간: ").append(reservation.time().getStartAt().toString());
            sb.append("\n---------------------");
        }
        System.out.println(sb);
    }

    public void printTimes(List<FindReservationTimeResponse> times) {
        StringBuilder sb = new StringBuilder();
        if (times.isEmpty()) {
            sb.append("시간 데이터가 존재하지 않습니다.");
        }
        for (FindReservationTimeResponse time : times) {
            sb.append("\n---------------------");
            sb.append("\n번호: ").append(time.id());
            sb.append("\n시간: ").append(time.startAt().toString());
            sb.append("\n---------------------");
        }
        System.out.println(sb);

    }

    public void printError(String message) {
        System.err.println("[ERROR] " + message);
    }
}
