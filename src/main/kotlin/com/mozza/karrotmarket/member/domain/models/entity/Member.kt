package com.mozza.karrotmarket.member.domain.models.entity

import com.mozza.karrotmarket.common.entity.BaseEntity
import com.mozza.karrotmarket.common.enum.MemberBadgeType
import com.mozza.karrotmarket.common.exception.AlreadyExistException
import com.mozza.karrotmarket.common.exception.UnchangedException
import com.mozza.karrotmarket.member.domain.models.value.Badge
import com.mozza.karrotmarket.member.persistence.MemberRepository

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "phone", nullable = false, unique = true)
    var phone: String,

    @Column(name = "nickname", nullable = false)
    var nickname: String,

    @Column(name = "image_url", nullable = false)
    var imageUrl: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "badge",
        joinColumns = [JoinColumn(name = "badge_id")]
    )
    @OrderColumn(name = "badge_idx")
    var badges: MutableSet<Badge>

) : BaseEntity() {

    fun updateEmail(email: String, memberRepository: MemberRepository) {
        if (memberRepository.existsByEmail(email))
            throw AlreadyExistException(AlreadyExistException.EMAIL_ALREADY_EXIST_EXCEPTION)
        this.email = email
    }

    fun updatePassword(password: String) {
        if (this.password == password)
            throw UnchangedException(UnchangedException.PASSWORD_UNCHANGED_EXCEPTION)
        this.password = password
    }

    fun updateNickname(nickname: String) {
        if (this.nickname == nickname)
            throw UnchangedException(UnchangedException.NAME_UNCHANGED_EXCEPTION)
        this.nickname = nickname
    }

    fun updatePhone(phone: String) {
        if (this.phone == phone)
            throw UnchangedException(UnchangedException.PHONE_UNCHANGED_EXCEPTION)
        this.phone = phone
    }

    fun firstDeal() {
        this.badges.add(Badge(MemberBadgeType.FIRST_DEAL))
    }

    fun tenthDeal() {
        this.badges.add(Badge(MemberBadgeType.TENTH_DEAL))
    }

    fun freeDeal() {
        this.badges.add(Badge(MemberBadgeType.FREE_DEAL))
    }
}
