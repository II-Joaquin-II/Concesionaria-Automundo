package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Pedido;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.id_pedido = :id")
    List<Pedido> findPedidoById(Long id);

    @Query("SELECT SUM(p.total) FROM Pedido p")
    BigDecimal totalVentas();

    long countByEstado(String estado);

    List<Pedido> findAllByOrderByFechaDesc(Pageable pageable);

}
