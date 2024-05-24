package com.example.twit.data.mappers

import com.example.twit.data.network.response.Node

interface TemplateMapper<I, O> {
    fun map(input: I): O
}