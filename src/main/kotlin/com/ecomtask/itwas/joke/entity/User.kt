package com.ecomtask.itwas.joke.entity

import com.ecomtask.itwas.joke.enumc.UserType
import jakarta.persistence.*

@Entity
@Table(name = "users", indexes = [Index(name = "login_index", columnList = "login", unique = true)])
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false)
    var login: String = "",

    @Column(nullable = false)
    var age: Int = 0,

    @Column(nullable = false)
    var password: String = "",

    var description: String? = null,

    @Enumerated(EnumType.STRING)
    var userType: UserType = UserType.NONE,

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    var courses: MutableSet<Course> = mutableSetOf()
)