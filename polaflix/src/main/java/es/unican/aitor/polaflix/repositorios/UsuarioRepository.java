package es.unican.aitor.polaflix.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.aitor.polaflix.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

}
