package mx.com.gm.dao;

import mx.com.gm.domain.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDAO extends CrudRepository<Persona, Long>{
    
}
