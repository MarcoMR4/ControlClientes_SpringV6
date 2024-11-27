package mx.com.gm.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.com.gm.domain.Persona;
import mx.com.gm.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ControladorInicio {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/")
    public String inicio(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model,
            @AuthenticationPrincipal User user
    ) {
        var personas = personaService.listarPersonas();
        log.info("Ejecutando el controlador Spring MVC");
        log.info("Usuario que hizo login: " + user);

        Pageable pageable = PageRequest.of(page, size);
        Page<Persona> personasPage = personaService.listarPersonasP(pageable);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personasPage.getTotalPages());
        model.addAttribute("totalItems", personasPage.getTotalElements());

        model.addAttribute("personas", personasPage.getContent());
        model.addAttribute("personasPage", personasPage);

        var saldoTotal = 0D;
        for (var p : personas) {
            saldoTotal += p.getSaldo();
        }
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());

        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona) {
        return "modificar";  // Retorna la vista para agregar o editar
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errors, Model model) {
        var error = false;

        if (errors.hasErrors()) {
            return "modificar";  // Retorna a la vista de modificación con los errores
        } else if (persona.getNombre().isEmpty() || persona.getApellido().isEmpty() || persona.getEmail().isEmpty()) {
            log.info("No hay nombre!");
            model.addAttribute("error", true);
            return "modificar";
        }

        log.info("No se capturo error");
        personaService.guardar(persona);
        return "redirect:/";
    }

    @GetMapping("/editar/{idPersona}")
    public String editar(Persona persona, Model model) {
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";  // Retorna la vista de modificación para editar la persona
    }

    @GetMapping("eliminar")
    public String eliminar(Persona persona) {
        persona = personaService.encontrarPersona(persona);
        personaService.eliminar(persona);
        return "redirect:/";  // Redirige a la página principal después de eliminar
    }

    //Controladores de seguridad con spring security
    @GetMapping("/login")
    public String login() {
        return "login"; // Nombre de la plantilla de Thymeleaf (login.html) 
    }

    @GetMapping("/errores/403")
    public String accessDenied() {
        return "errores/403"; // Nombre de la plantilla de Thymeleaf (403.html) 
    }

    @GetMapping("/page/{page}") //Cargando página 
    public String paginacion(
            @PathVariable int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model,
            @AuthenticationPrincipal User user
    ) {
        var personas = personaService.listarPersonas();
        log.info("Ejecutando el controlador Spring MVC");
        log.info("Usuario que hizo login: " + user);

        Pageable pageable = PageRequest.of(page, size);
        Page<Persona> personasPage = personaService.listarPersonasP(pageable);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personasPage.getTotalPages());
        model.addAttribute("totalItems", personasPage.getTotalElements());

        model.addAttribute("personas", personas);
        model.addAttribute("personasPage", personasPage);

        var saldoTotal = 0D;
        for (var p : personas) {
            saldoTotal += p.getSaldo();
        }
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());

        return "index";
    }

}
