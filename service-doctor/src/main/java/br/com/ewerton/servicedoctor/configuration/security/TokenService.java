package br.com.ewerton.servicedoctor.configuration.security;


import br.com.ewerton.servicedoctor.model.DoctorModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.bouncycastle.math.ec.rfc8032.Ed448;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.embedded.TomcatVirtualThreadsWebServerFactoryCustomizer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

   @Value("${api.security.token.secret}")
    private String secret;


   public  String generateToken(DoctorModel doctorModel){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("auth-api").withSubject(doctorModel.getEmail()).withExpiresAt(genExpiresTime()).sign(algorithm);
            return token;
        } catch(JWTCreationException e){
            throw new RuntimeException("Error while  generating token", e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
           return "";
        }
    }

    private Instant genExpiresTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
