package com.bondary.member

import com.fasterxml.jackson.annotation.JsonValue

@JvmInline
value class SocialId(@JsonValue val value: String)