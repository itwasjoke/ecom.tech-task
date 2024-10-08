package com.ecomtask.itwas.joke.entity

import com.ecomtask.itwas.joke.enums.UserType
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var username: String = "",

    @Column(nullable = false)
    var age: Int = 0,

    @Column(nullable = false)
    var password: String = "",

    var description: String? = null,

    @Enumerated(EnumType.STRING)
    var userType: UserType = UserType.NONE,

    @ManyToMany(mappedBy = "students")
    var courses: MutableSet<Course> = mutableSetOf()
)