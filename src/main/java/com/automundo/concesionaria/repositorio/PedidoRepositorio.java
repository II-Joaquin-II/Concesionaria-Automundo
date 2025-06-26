package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.id_pedido = :id")
    List<Pedido> findPedidoById(Long id);
}
