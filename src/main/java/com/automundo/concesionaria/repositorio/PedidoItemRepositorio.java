package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.PedidoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepositorio extends JpaRepository<PedidoItem, Long> {

    @Query("SELECT pi FROM PedidoItem pi WHERE pi.pedido.id_pedido = :id")
    List<PedidoItem> findPedidoById(Long id);
}
