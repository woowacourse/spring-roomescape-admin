package roomescape.time.service;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import roomescape.reservation.domain.ReservationTime;
import roomescape.time.exception.TimeNotFoundException;
import roomescape.time.repository.TimeRepository;

@Service
public class TimeServiceImpl implements TimeService {
  private final TimeRepository timeRepository;

  public TimeServiceImpl(TimeRepository timeRepository) {
    this.timeRepository = timeRepository;
  }

  @Override
  public ReservationTime create(String startAt) {
    return timeRepository.save(startAt);
  }

  @Override
  public List<ReservationTime> findAll() {
    return timeRepository.findAll();
  }

  @Override
  public ReservationTime findById(long id) {
    try {
      return timeRepository.findById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new TimeNotFoundException(id);
    }
  }

  @Override
  public void deleteById(long id) {
    boolean deleted = timeRepository.deleteById(id);
    if (!deleted) {
      throw new TimeNotFoundException(id);
    }
  }

}
