package com.kotlin.kotlinspringboot.domain.user

import com.kotlin.kotlinspringboot.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class User(name: String, email: String, picture: String, role: Role): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(nullable = false)
    var name: String = name

    @Column(nullable = false)
    var email: String = email

    @Column
    var picture: String = picture

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = role

    fun update(name: String, picture: String) : User {
        this.name = name
        this.picture = picture

        return this
    }

    fun getRoleKey(): String = this.role.key
}