package com.bondary.service.liveSearch

import com.bondary.model.User
import com.bondary.persistence.jpa.member.entity.MemberEntity
import com.bondary.persistence.jpa.member.repository.MemberJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class SearchService(
    private val memberRepository: MemberJpaRepository
) {
    /**
     * 이름으로 사용자를 검색하여 리스트로 반환
     */
    @Transactional(readOnly = true)
    fun searchUsers(name: String, limit: Int): Mono<List<User>> {
        return Mono.fromCallable {
            val pageable: Pageable = PageRequest.of(0, limit)
            val members = memberRepository.findByNameContainingIgnoreCase(name, pageable)
            members.map { mapMemberToUser(it) }
        }.subscribeOn(Schedulers.boundedElastic())
    }

    /**
     * 이름으로 사용자를 검색하여 실시간 스트림으로 반환 (SSE용)
     */
    @Transactional(readOnly = true)
    fun searchUsersReactive(name: String, limit: Int): Flux<User> {
        return Flux.defer {
            val pageable = PageRequest.of(0, limit)
            val members = memberRepository.findByNameContainingIgnoreCase(name, pageable)
            Flux.fromIterable(members.map { mapMemberToUser(it) })
        }.subscribeOn(Schedulers.boundedElastic())
    }

    /**
     * MemberEntity를 User 모델로 변환
     */
    private fun mapMemberToUser(member: MemberEntity): User {
        return User(
            id = member.id,
            name = member.name,
            profileImage = member.profileImage,
            introduction = member.introduction,
            schoolName = member.schoolName,
            firstMajorName = member.firstMajorName,
            secondaryMajorName = member.secondaryMajorName,
            interestArea = member.interestArea,
            interestJob = member.interestJob,
            instagram = member.instagram,
            linkedin = member.linkedin,
            etcLinks = member.etcLinks,
            onBoardingAt = member.onBoardingAt
        )
    }
}