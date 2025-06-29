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

    @Query("SELECT pi.accesorio.nombre, COUNT(pi) FROM PedidoItem pi GROUP BY pi.accesorio.nombre ORDER BY COUNT(pi) DESC")
    List<Object[]> contarAccesoriosVendidos();
}
