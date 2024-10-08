package com.ecomtask.itwas.joke.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "courses")
data class Course (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = false)
    var dateStart: Date = Date(),

    @Column(nullable = false)
    var dateEnd: Date = Date(),

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "courses_students",
        joinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var students: MutableSet<User> = mutableSetOf(),

    @ManyToOne
    @JoinColumn(name = "turn_id", nullable = false)
    var creator: User? = null
)