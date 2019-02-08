package de.hhu.propra2.propay

import org.springframework.data.repository.CrudRepository

interface ReservationRepository : CrudRepository<Reservation, Long> {

}
