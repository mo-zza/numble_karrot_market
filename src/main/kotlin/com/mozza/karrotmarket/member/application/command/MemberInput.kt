package com.mozza.karrotmarket.member.application.command

import com.mozza.karrotmarket.member.domain.models.entity.Member
import jakarta.annotation.Nullable
import java.util.*

data class MemberInput(
    @Nullable
    val id: UUID?,
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
    val nickname: String,
    val imageUrl: String,
) {
    fun isIdParamNull(): Boolean {
        return id == null
    }

    fun toEntity(state: Boolean): Member {
        return Member(email, password, name, phone, nickname, state, imageUrl)
    }
}