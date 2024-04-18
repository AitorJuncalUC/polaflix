package es.unican.aitor.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.aitor.spring.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

}
