package br.com.diego.brewer.repository;

import br.com.diego.brewer.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where lower(u.email) = lower(:email) and u.ativo = true")
    Optional<Usuario> porEmailEAtivo(@Param("email") String email);

    @Query("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario")
    List<String> permissoes(@Param("usuario") Usuario usuario);

    List<Usuario> findByCodigoIn(Collection<Long> codigo);
}


