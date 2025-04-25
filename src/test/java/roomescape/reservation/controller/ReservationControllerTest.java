package roomescape.reservation.controller;

class ReservationControllerTest {

//    private FakeReservationService reservationService;
//    private ReservationController reservationController;
//
//    @BeforeEach
//    void init() {
//        reservationService = new FakeReservationService();
//        reservationController = new ReservationController(reservationService);
//    }
//
//    @DisplayName("모든 예약 정보를 가져온다.")
//    @Test
//    void test1() {
//        // given
//        List<String> names = List.of("꾹", "드라고", "히로");
//        LocalDate now = LocalDate.now();
//        LocalTime time = LocalTime.now();
//        Long timeId = 1L;
//
//        reservationService.addReservationTime(timeId, new ReservationTime(timeId, time));
//
//        for (String name : names) {
//            ReservationRequest request = new ReservationRequest(name, now, timeId);
//            reservationService.save(request);
//        }
//
//        // when
//        ResponseEntity<List<ReservationResponse>> result = reservationController.readAllReservations();
//        List<ReservationResponse> body = result.getBody();
//
//        // then
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Assertions.assertNotNull(body);
//
//        List<String> resultNames = body.stream().map(ReservationResponse::name).toList();
//        List<LocalDate> resultDates = body.stream().map(ReservationResponse::date).toList();
//        List<LocalTime> resultTimes = body.stream()
//                .map(ReservationResponse::time)
//                .map(ReservationTimeResponse::startAt)
//                .toList();
//
//        assertThat(resultNames).containsExactlyElementsOf(names);
//
//        SoftAssertions softly = new SoftAssertions();
//        for (LocalDate dateTime : resultDates) {
//            softly.assertThat(dateTime).isEqualTo(now);
//        }
//        for (LocalTime resultTime : resultTimes) {
//            softly.assertThat(resultTime).isEqualTo(time);
//        }
//        softly.assertAll();
//    }
//
//    @DisplayName("예약 정보를 추가한다.")
//    @ParameterizedTest
//    @CsvSource(value = {"꾹,2025-04-17,1"}, delimiter = ',')
//    void test2(String name, String date, Long timeId) {
//        // given
//        LocalTime localTime = LocalTime.now();
//        ReservationTime time = new ReservationTime(timeId, localTime);
//        reservationService.addReservationTime(timeId, time);
//
//        LocalDate localDate = LocalDate.parse(date);
//
//        ReservationRequest request = new ReservationRequest(name, localDate, timeId);
//        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(timeId, localTime);
//        ReservationResponse expected = new ReservationResponse(1L, name, localDate, reservationTimeResponse);
//
//        // when
//        ResponseEntity<ReservationResponse> result = reservationController.save(request);
//
//        // then
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(result.getBody()).isEqualTo(expected);
//    }
//
//    @DisplayName("예약 정보를 삭제한다.")
//    @Test
//    void test3() {
//        // given
//        long id = 1L;
//        ReservationTime reservationTime = new ReservationTime(id, LocalTime.now());
//        Reservation reservation = new Reservation(id, "꾹", LocalDate.now(), reservationTime);
//
//        reservationService.addReservation(id, reservation);
//
//        // when & then
//        assertThatCode(() -> reservationController.delete(id))
//                .doesNotThrowAnyException();
//    }
//
//    @DisplayName("삭제할 예약 정보가 없다면 예외를 반환한다")
//    @Test
//    void test4() {
//        // given
//        long id = 1L;
//
//        // when & then
//        assertThatThrownBy(() -> reservationController.delete(id))
//                .isInstanceOf(EntityNotFoundException.class);
//    }
}
