package com.bondary

import com.fasterxml.jackson.annotation.JsonValue

@JvmInline
value class SocialId(@JsonValue val value: String)