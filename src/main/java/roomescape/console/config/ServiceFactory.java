package roomescape.console.config;

import roomescape.console.dao.MemoryReservationDao;
import roomescape.console.dao.MemoryReservationTimeDao;
import roomescape.core.service.ReservationService;
import roomescape.core.service.ReservationTimeService;

public class ServiceFactory {

    private static final ServiceFactory serviceFactory = new ServiceFactory();
    private static final ReservationService reservationService;
    private static final ReservationTimeService reservationTimeService;

    static {
        MemoryReservationDao memoryReservationDao = new MemoryReservationDao();
        MemoryReservationTimeDao memoryReservationTimeDao = new MemoryReservationTimeDao();

        reservationService = new ReservationService(memoryReservationDao, memoryReservationTimeDao);
        reservationTimeService = new ReservationTimeService(memoryReservationTimeDao);
    }

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public ReservationTimeService getReservationTimeService() {
        return reservationTimeService;
    }
}
