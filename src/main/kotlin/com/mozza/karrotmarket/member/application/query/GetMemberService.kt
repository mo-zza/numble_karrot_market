package com.mozza.karrotmarket.member.application.query

import com.mozza.karrotmarket.common.exception.NotFoundException
import com.mozza.karrotmarket.member.domain.models.entity.Member
import com.mozza.karrotmarket.member.persistence.MemberRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetMemberService(private val memberRepository: MemberRepository) {

    fun getMemberById(id: UUID): Member {
        return memberRepository.findById(id)
            .orElseThrow { throw NotFoundException(NotFoundException.USER_NOT_FOUND_EXCEPTION) }
    }

    fun getMemberByEmail(email: String): Member {
        if (!memberRepository.existsByEmail(email)) {
            throw NotFoundException(NotFoundException.USER_NOT_FOUND_EXCEPTION)
        }
        return memberRepository.findByEmail(email)
    }

    fun getMembers(): List<Member> {
        return memberRepository.findAll()
    }
}