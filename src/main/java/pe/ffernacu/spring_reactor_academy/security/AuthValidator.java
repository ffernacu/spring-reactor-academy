package pe.ffernacu.spring_reactor_academy.security;

import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    public boolean hasAccess(){
        //logica de negocio
        return true;
    }
}
