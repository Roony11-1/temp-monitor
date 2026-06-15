package io.github.roony11_1.kernel.security;

import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordHasher 
{
    private static final int LOG_ROUNDS = 12;
    
    public String hash(String password) 
    {
        if (password == null || password.isEmpty()) 
            throw new IllegalArgumentException("Password no puede ser null o vacío");

        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }
    
    public boolean verify(String password, String hash) 
    {
        if (password == null || hash == null)
            return false;
        
        return BCrypt.checkpw(password, hash);
    }
}