package com.mozza.karrotmarket.member.domain.models.value

import com.mozza.karrotmarket.common.enum.MemberBadgeType
import com.mozza.karrotmarket.common.value.BaseValue
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class Badge(
    badge: MemberBadgeType
) : BaseValue() {

    @Column(name = "badge", nullable = false)
    @Enumerated(EnumType.STRING)
    var badge: MemberBadgeType = badge
}