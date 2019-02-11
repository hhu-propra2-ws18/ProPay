package de.hhu.propra2.propay.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

//TODO: timestatmps
@Entity
data class Reservation(@Id @GeneratedValue var id: Long? = null,
                       val amount: Double,
                       @OneToOne @JsonIgnore val targetAccount: Account)
