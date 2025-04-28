package roomescape.presentation.console.view;

import org.springframework.stereotype.Component;
import roomescape.presentation.dto.request.ReservationCreateRequest;
import roomescape.presentation.dto.request.ReservationTimeCreateRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class InputView {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public CommandType getCommandType() {
        System.out.print("""
                메뉴를 선택해주세요.
                (1 : 예약 추가), (2 : 예약 전체 조회), (3 : 예약 삭제),
                (4 : 예약 시간 추가), (5 : 예약 시간 전체 조회), (6 : 예약 시간 삭제)
                (7 : 종료)
                """);

        final String input = readLine();
        return CommandType.from(Integer.parseInt(input.trim()));
    }

    public ReservationCreateRequest getReservationCreateRequest() {
        System.out.print("""
                추가할 예약 정보를 입력해주세요. (이름,날짜,예약시간 id)
                ex) 돔푸,2024-05-17,1
                """);
        final String[] inputs = readLine().split(",");
        return new ReservationCreateRequest(
                inputs[0],
                LocalDate.parse(inputs[1]),
                Long.parseLong(inputs[2])
        );
    }

    public long getDeleteReservationId() {
        System.out.print("""
                삭제할 예약 정보를 입력해주세요. (예약 id)
                ex) 1
                """);
        final String input = readLine();
        return Long.parseLong(input);
    }

    public ReservationTimeCreateRequest getReservationTimeCreateRequest() {
        System.out.print("""
                추가할 예약 시간 정보를 입력해주세요. (예약 시간)
                ex) 10:00
                """);
        final String input = readLine();
        return new ReservationTimeCreateRequest(LocalTime.parse(input));
    }

    public long getDeleteReservationTimeId() {
        System.out.print("""
                삭제할 예약 시간 정보를 입력해주세요. (예약 시간 id)
                ex) 1
                """);
        final String input = readLine();
        return Long.parseLong(input);
    }

    private static String readLine() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
