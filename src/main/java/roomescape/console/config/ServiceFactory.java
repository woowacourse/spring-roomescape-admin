package roomescape.console.config;

import roomescape.console.dao.MemoryReservationDao;
import roomescape.console.dao.MemoryReservationTimeDao;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private static final ReservationService RESERVATION_SERVICE;
    private static final ReservationTimeService RESERVATION_TIME_SERVICE;

    static {
        MemoryReservationDao memoryReservationDao = new MemoryReservationDao();
        MemoryReservationTimeDao memoryReservationTimeDao = new MemoryReservationTimeDao();

        RESERVATION_SERVICE = new ReservationService(memoryReservationDao, memoryReservationTimeDao);
        RESERVATION_TIME_SERVICE = new ReservationTimeService(memoryReservationTimeDao);
    }

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public ReservationService getReservationService() {
        return RESERVATION_SERVICE;
    }

    public ReservationTimeService getReservationTimeService() {
        return RESERVATION_TIME_SERVICE;
    }
}
