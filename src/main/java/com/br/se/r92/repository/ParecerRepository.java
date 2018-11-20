package com.br.se.r92.repository;

import com.br.se.r92.domain.Parecer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Parecer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParecerRepository extends JpaRepository<Parecer, Long>, JpaSpecificationExecutor<Parecer> {

    @Query("select tb_parecer from Parecer tb_parecer where tb_parecer.usuario.login = ?#{principal.username}")
    List<Parecer> findByUsuarioIsCurrentUser();

    @Query("select tb_parecer from Parecer tb_parecer where tb_parecer.processo.id = :id")
    List<Parecer> findAllByIdProcesso(@Param("id") Long id);

}
