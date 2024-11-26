package mx.com.gm.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data 
@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotEmpty(message = "El apellido no puede estar vacío")
    private String apellido;
    
    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    private String telefono;
    
    @NotNull
    private Double saldo;
}
