package de.hhu.propra2.propay

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

// TODO: timestamps
@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["team", "account"])])
data class Account(@Id @GeneratedValue @JsonIgnore var id: Double? = null,
                   var team: String,
                   var account: String,
                   var amount: Double,
                   @OneToMany var reservations: List<Reservation> = Collections.emptyList()) {

    fun canCover(amount: Double): Boolean {
        val blocked = reservations.sumByDouble { reservation -> reservation.amount }
        return this.amount - blocked >= amount
    }
}

