package com.mozza.karrotmarket.member.application.command

import com.mozza.karrotmarket.common.exception.BadRequestException
import com.mozza.karrotmarket.common.exception.NotFoundException
import com.mozza.karrotmarket.member.domain.models.entity.Member
import com.mozza.karrotmarket.member.persistence.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UpdateMemberService(private val memberRepository: MemberRepository) {

    @Transactional
    fun updateAll(memberInput: MemberInput): Member {
        if (memberInput.id != null) {
            val member = memberRepository.findById(memberInput.id)
                .orElseThrow { throw NotFoundException(NotFoundException.USER_NOT_FOUND_EXCEPTION) }
            member.updateEmail(memberInput.email, memberRepository)
            member.updateNickname(memberInput.nickname)
            member.updatePassword(memberInput.password)
            member.updatePhone(memberInput.phone)
            memberRepository.save(member)
        }

        throw BadRequestException(" is must be have", "id")
    }

    @Transactional
    fun deleteMemberById(id: UUID) {
        memberRepository.findById(id)
            .orElseThrow { throw NotFoundException(NotFoundException.USER_NOT_FOUND_EXCEPTION) }
            .delete()
    }

    @Transactional
    fun deleteMemberByEmail(email: String) {
        if (!memberRepository.existsByEmail(email)) {
            throw NotFoundException(NotFoundException.USER_NOT_FOUND_EXCEPTION)
        }

        memberRepository.findByEmail(email).delete()
    }
}