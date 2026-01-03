package com.badminton.booking.repository
import com.badminton.booking.entity.CourtTimeBlock
import org.springframework.data.jpa.repository.JpaRepository

interface CourtTimeBlockRepository : JpaRepository<CourtTimeBlock, Long> {
    fun findByCourtIdAndDate(courtId: Long, date: String): List<CourtTimeBlock>
}