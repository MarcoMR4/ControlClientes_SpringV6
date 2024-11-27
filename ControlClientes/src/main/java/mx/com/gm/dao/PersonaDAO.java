package mx.com.gm.dao;

import mx.com.gm.domain.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDAO extends CrudRepository<Persona, Long>{

    public Page<Persona> findAll(Pageable pageable);
    
}
