package com.example.anipro.data.mappers

interface TemplateMapper<I, O> {
    fun map(input: I): O
}