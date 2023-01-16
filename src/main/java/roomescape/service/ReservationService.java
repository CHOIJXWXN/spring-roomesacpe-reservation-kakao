package roomescape.service;

import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationShowResponse;
import roomescape.exception.DuplicateReservationScheduleException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ThemeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;
import roomescape.domain.Reservation;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.ReservationCreateRequest;

import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    private void checkSchedule(ReservationCreateRequest reservationCreateRequest) {
        if (reservationRepository.checkSchedule(reservationCreateRequest.getDate(), reservationCreateRequest.getTime()) != 0) {
            throw new DuplicateReservationScheduleException("중복된 예약 발생");
        }
    }

    @Transactional
    public Reservation createReservation(ReservationCreateRequest reservationCreateRequest) {
        checkSchedule(reservationCreateRequest);
        Theme theme = themeRepository.findThemeById(reservationCreateRequest.getThemeId()).orElseThrow(ThemeNotFoundException::new);
        Long id = reservationRepository.addReservation(reservationCreateRequest.toReservation(theme));
        return reservationRepository.findReservation(id).orElseThrow(ReservationNotFoundException::new);
    }

    public ReservationShowResponse showReservation(Long id) {
        Reservation reservation = reservationRepository.findReservation(id).orElseThrow(ReservationNotFoundException::new);
        return ReservationShowResponse.of(reservation);
    }

    @Transactional
    public int deleteReservation(Long id) {
        return reservationRepository.removeReservation(id);
    }

}
