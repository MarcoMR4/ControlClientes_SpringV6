package mx.com.gm.servicio;

import java.util.List;
import mx.com.gm.domain.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonaService {
    
    public List<Persona> listarPersonas();
    
    public void guardar(Persona persona);
    
    public void eliminar(Persona persona);
    
    public Persona encontrarPersona(Persona persona);
    
    Page<Persona> listarPersonasP(Pageable pageable);

}
