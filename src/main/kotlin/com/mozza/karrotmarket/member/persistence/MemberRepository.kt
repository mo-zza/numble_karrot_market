package com.mozza.karrotmarket.member.persistence

import com.mozza.karrotmarket.member.domain.models.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MemberRepository : JpaRepository<Member, UUID> {

    fun existsByEmail(email: String): Boolean
}