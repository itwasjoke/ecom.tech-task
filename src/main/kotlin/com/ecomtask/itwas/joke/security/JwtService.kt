package com.ecomtask.itwas.joke.security

import com.ecomtask.itwas.joke.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService {
    @Value("\${token.signing.key}")
    private val jwtSigningKey: String? = null
    fun extractUserName(token: String): String {
        return extractClaim<String>(token, Claims::getSubject)
    }

    fun generateToken(userCreated: User): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["id"] = userCreated.id
        claims["role"] = userCreated.userType
        return generateToken(claims, userCreated)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolvers: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolvers.apply(claims)
    }

    private fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 100000 * 60 * 24))
            .signWith(signingKey, SignatureAlgorithm.HS256).compact()
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim<Date>(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody()
    }

    private val signingKey: Key
        private get() {
            val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSigningKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }
}