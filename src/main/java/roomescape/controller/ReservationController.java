package roomescape.controller;

import roomescape.domain.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationShowResponse;
import roomescape.service.ReservationService;

import java.net.URI;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationShowResponse> showReservation(@PathVariable Long id) {
        ReservationShowResponse reservationShowResponse = reservationService.showReservation(id);
        return ResponseEntity.ok(reservationShowResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}
