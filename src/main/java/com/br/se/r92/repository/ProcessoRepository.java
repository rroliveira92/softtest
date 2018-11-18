package com.br.se.r92.repository;

import com.br.se.r92.domain.Processo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Processo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long>, JpaSpecificationExecutor<Processo> {

    @Query("select tb_processo from Processo tb_processo where tb_processo.usuarioCadastro.login = ?#{principal.username}")
    List<Processo> findByUsuarioCadastroIsCurrentUser();

    @Query("select tb_processo from Processo tb_processo where tb_processo.usuarioAtualizacao.login = ?#{principal.username}")
    List<Processo> findByUsuarioAtualizacaoIsCurrentUser();
    @Query("select distinct tb_processo from Processo tb_processo left join fetch tb_processo.usuariosParecers")
    List<Processo> findAllWithEagerRelationships();

    @Query("select tb_processo from Processo tb_processo left join fetch tb_processo.usuariosParecers where tb_processo.id =:id")
    Processo findOneWithEagerRelationships(@Param("id") Long id);

}
