package com.automundo.concesionaria.repositorio;

import com.automundo.concesionaria.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepositorio extends JpaRepository<PedidoItem, Long>{
    
}
