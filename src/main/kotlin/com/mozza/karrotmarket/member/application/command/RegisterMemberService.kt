package com.mozza.karrotmarket.member.application.command

import com.mozza.karrotmarket.member.domain.models.entity.Member
import com.mozza.karrotmarket.member.persistence.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterMemberService(private val memberRepository: MemberRepository) {

    @Transactional
    fun register(memberInput: MemberInput): Member {
        return memberInput.toEntity(true).saveOrAlreadyExistException(memberRepository)
    }
}