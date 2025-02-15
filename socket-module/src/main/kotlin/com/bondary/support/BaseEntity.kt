package com.bondary.support

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

open class BaseEntity {
    @CreatedDate
    @Field("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
}
