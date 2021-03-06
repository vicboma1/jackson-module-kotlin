package com.fasterxml.jackson.module.kotlin.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

public class TestJacksonWithKotlinBuiltins {
    private val mapper: ObjectMapper = jacksonObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, false)

    private data class ClassWithPair(val name: Pair<String, String>, val age: Int)
    @Test fun testPair() {
        val json = """{"name":{"first":"John","second":"Smith"},"age":30}"""
        val expected = ClassWithPair(Pair("John", "Smith"), 30)

        assertThat(mapper.writeValueAsString(expected), equalTo(json))
        val stateObj = mapper.readValue<ClassWithPair>(json)
        assertThat(stateObj, equalTo(expected))
    }

    private data class ClassWithPairMixedTypes(val person: Pair<String, Int>)
    @Test fun testPairMixedTypes() {
        val json = """{"person":{"first":"John","second":30}}"""
        val expected = ClassWithPairMixedTypes(Pair("John", 30))

        assertThat(mapper.writeValueAsString(expected), equalTo(json))
        val stateObj = mapper.readValue<ClassWithPairMixedTypes>(json)
        assertThat(stateObj, equalTo(expected))
    }

    private data class ClassWithTriple(val name: Triple<String, String, String>, val age: Int)
    @Test fun testTriple() {
        val json = """{"name":{"first":"John","second":"Davey","third":"Smith"},"age":30}"""
        val expected = ClassWithTriple(Triple("John", "Davey", "Smith"), 30)

        assertThat(mapper.writeValueAsString(expected), equalTo(json))
        val stateObj = mapper.readValue<ClassWithTriple>(json)
        assertThat(stateObj, equalTo(expected))
    }

    private data class ClassWithRanges(val ages: IntRange, val distance: LongRange)
    @Test fun testRanges() {
        val json = """{"ages":{"start":18,"end":40},"distance":{"start":5,"end":50}}"""
        val expected = ClassWithRanges(IntRange(18, 40), LongRange(5, 50))

        assertThat(mapper.writeValueAsString(expected), equalTo(json))
        val stateObj = mapper.readValue<ClassWithRanges>(json)
        assertThat(stateObj, equalTo(expected))
    }
}
