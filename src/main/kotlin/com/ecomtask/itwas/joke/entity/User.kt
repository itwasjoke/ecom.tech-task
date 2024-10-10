package com.ecomtask.itwas.joke.entity

import com.ecomtask.itwas.joke.enumc.UserType
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users", indexes = [Index(name = "login_index", columnList = "login", unique = true)])
class User: UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var fullname: String = ""

    @Column(nullable = false)
    var login: String = ""

    @Column(nullable = false)
    var age: Int = 0

    @Column(nullable = false)
    var rawPassword: String = ""

    var description: String? = null

    @Enumerated(EnumType.STRING)
    var userType: UserType = UserType.NONE

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonIgnore
    var courses: MutableSet<Course> = mutableSetOf()
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_$userType"))
    }

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var coursesWhatCreated: MutableSet<Course> = mutableSetOf()

    override fun getPassword(): String {
        return rawPassword
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}