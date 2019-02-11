package de.hhu.propra2.propay.repositories

import de.hhu.propra2.propay.entities.Reservation
import org.springframework.data.repository.CrudRepository

interface ReservationRepository : CrudRepository<Reservation, Long> {

}
