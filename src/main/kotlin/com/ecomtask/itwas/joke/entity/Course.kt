package com.ecomtask.itwas.joke.entity

import jakarta.persistence.*
import java.util.Date

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

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "courses_students",
        joinColumns = [JoinColumn(name = "course_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var students: MutableSet<User> = mutableSetOf(),

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    var creator: User? = null
)