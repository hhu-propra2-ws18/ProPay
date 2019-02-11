package de.hhu.propra2.propay.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

// TODO: timestamps
@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["account"])])
data class Account(@Id @GeneratedValue @JsonIgnore var id: Long? = null,
                   var account: String,
                   var amount: Double,
                   @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true) var reservations: MutableList<Reservation> = Collections.emptyList()) {

    fun canCover(amount: Double): Boolean {
        val blocked = reservations.sumByDouble { reservation -> reservation.amount }
        return this.amount - blocked >= amount
    }

    fun availableAmount() =
            this.amount - reservations.sumByDouble { reservation -> reservation.amount }
}

