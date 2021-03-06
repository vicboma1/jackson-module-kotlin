package com.fasterxml.jackson.module.kotlin.test

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


data class DataClassPerson(val name: String, val age: Int)

public class TestM11Changes {
    val mapper = jacksonObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, false)

    private class Class_With_One_Constructor(val name: String, val age: Int)

    @Test fun testNormalClass_One_Constructor() {
        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = Class_With_One_Constructor("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_With_One_Constructor>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson.name, equalTo(expectedPerson.name))
        assertThat(newPerson.age, equalTo(expectedPerson.age))
    }

    private data class Class_Data_Annotation_With_One_Constructor(val name: String, val age: Int)

    @Test fun testDataClass_One_Constructor() {


        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = Class_Data_Annotation_With_One_Constructor("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_Data_Annotation_With_One_Constructor>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson, equalTo(expectedPerson))
    }

    private data class Class_With_Init_Constructor(val name: String, val age: Int) {
        val otherThing: String
        init {
            otherThing = "franky"
        }
    }

    @Test fun testDataClass_Init_Constructor() {

        val expectedJson = """{"name":"John Smith","age":30,"otherThing":"franky"}"""
        val expectedPerson = Class_With_Init_Constructor("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_With_Init_Constructor>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson, equalTo(expectedPerson))
    }

    private data class Class_With_Init_Constructor_And_Ignored_Property(val name: String, val age: Int) {
        @JsonIgnore val otherThing: String
        init {
            otherThing = "franky"
        }
    }

    @Test fun testDataClass_Init_Constructor_And_Ignored_Property() {

        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = Class_With_Init_Constructor_And_Ignored_Property("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_With_Init_Constructor_And_Ignored_Property>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson, equalTo(expectedPerson))
    }

    private class Class_With_No_Field_Parameters_But_Field_Declared_Inside_initialized_from_parameter(val name: String, age: Int) {
        val age: Int = age
    }

    @Test fun testDataClass_With_No_Field_Parameters_But_Field_Declared_Inside_initialized_from_parameter() {

        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = Class_With_No_Field_Parameters_But_Field_Declared_Inside_initialized_from_parameter("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_With_No_Field_Parameters_But_Field_Declared_Inside_initialized_from_parameter>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson.name, equalTo(expectedPerson.name))
        assertThat(newPerson.age, equalTo(expectedPerson.age))
    }

    private class ClassFor_testDataClass_WithOnlySecondaryConstructor {
        val name: String
        val age: Int
        constructor(name: String, age: Int) {
           this.name = name
            this.age = age
        }
    }

    @Test fun testDataClass_WithOnlySecondaryConstructor() {

        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = ClassFor_testDataClass_WithOnlySecondaryConstructor("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<ClassFor_testDataClass_WithOnlySecondaryConstructor>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson.name, equalTo(expectedPerson.name))
        assertThat(newPerson.age, equalTo(expectedPerson.age))
    }


    private class Class_WithPrimaryAndSecondaryConstructor(val name: String, val age: Int) {
        constructor(nameAndAge: String) : this(nameAndAge.substringBefore(':'), nameAndAge.substringAfter(':').toInt()) {

        }
    }

    @Test fun testDataClass_WithPrimaryAndSecondaryConstructor() {

        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = Class_WithPrimaryAndSecondaryConstructor("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_WithPrimaryAndSecondaryConstructor>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson.name, equalTo(expectedPerson.name))
        assertThat(newPerson.age, equalTo(expectedPerson.age))
    }

    private class Class_WithPrimaryAndSecondaryConstructorAnnotated(name: String) {
        val name: String = name
        var age: Int = 0
        @JsonCreator constructor(name: String, age: Int) : this(name) {
            this.age = age
        }
    }

    @Test fun testDataClass_WithPrimaryAndSecondaryConstructorBothCouldBeUsedToDeserialize() {

        val expectedJson = """{"name":"John Smith","age":30}"""
        val expectedPerson = Class_WithPrimaryAndSecondaryConstructorAnnotated("John Smith", 30)

        val actualJson = mapper.writeValueAsString(expectedPerson)
        val newPerson  = mapper.readValue<Class_WithPrimaryAndSecondaryConstructorAnnotated>(actualJson)

        assertThat(actualJson, equalTo(expectedJson))
        assertThat(newPerson.name, equalTo(expectedPerson.name))
        assertThat(newPerson.age, equalTo(expectedPerson.age))
    }


}