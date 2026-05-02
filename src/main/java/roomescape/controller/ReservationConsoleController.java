package roomescape.controller;

import java.util.List;
import java.util.Scanner;
import roomescape.repository.ReservationTimeRepository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;
import roomescape.service.ReservationService;

// (선택) 콘솔 UI -> UI가 바뀌어도 비즈니스 로직은 수정될 필요 없다.
public class ReservationConsoleController {

    private final ReservationService reservationService;
    private final ReservationTimeRepository reservationTimeRepository;
    private final Scanner scanner = new Scanner(System.in);

    public ReservationConsoleController(ReservationService reservationService, ReservationTimeRepository reservationTimeRepository) {
        this.reservationService = reservationService;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void run() {
        while (true) {
            printStart();
            int input = scanner.nextInt();
            if (input == 1) {
                printReservations(reservationService.selectReservations());
            }
            if (input == 2) {
                insertReservation();
            }
            if (input == 3) {
                deleteReservation();
            }
            if (input == 4) {
                printTimes(reservationTimeRepository.select());
            }
            if (input == 5) {
                insertTime();
            }
            if (input == 6) {
                deleteTime();
            }
        }
    }

    private void printStart() {
        System.out.println("방탈출 예약 시스템입니다. 원하는 메뉴 번호를 입력하세요.");
        System.out.println("1. 전체 예약 조회");
        System.out.println("2. 예약 생성");
        System.out.println("3. 예약 삭제");
        System.out.println("4. 전체 시간 조회");
        System.out.println("5. 시간 추가");
        System.out.println("6. 시간 삭제");
    }

    private void printReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    private void insertReservation() {
        printTimes(reservationTimeRepository.select());
        System.out.print("이름을 입력하세요: ");
        String name = scanner.next();
        System.out.print("날짜를 입력하세요: ");
        String date = scanner.next();
        System.out.print("timeId를 입력하세요: ");
        long timeId = scanner.nextLong();
        reservationService.createReservation(new ReservationRequest(name, date, timeId));
    }

    private void deleteReservation() {
        printReservations(reservationService.selectReservations());
        System.out.print("삭제할 예약 id를 입력하세요: ");
        long id = scanner.nextLong();
        reservationService.deleteReservation(id);
    }

    private void printTimes(List<ReservationTime> times) {
        for (ReservationTime time : times) {
            System.out.println(time);
        }
    }

    private void insertTime() {
        System.out.print("추가할 시간을 입력하세요 (HH:mm): ");
        String startAt = scanner.next();
        reservationTimeRepository.insert(new ReservationTimeRequest(startAt));
    }

    private void deleteTime() {
        printTimes(reservationTimeRepository.select());
        System.out.print("삭제할 시간 id를 입력하세요: ");
        long id = scanner.nextLong();
        reservationTimeRepository.delete(id);
    }
}
