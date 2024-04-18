package com.example.twit.data.mappers

import com.example.twit.data.network.response.node

interface TemplateMapper<I, O> {
    fun map(input: node): O
}