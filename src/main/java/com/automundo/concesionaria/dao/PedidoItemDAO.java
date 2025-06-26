package com.automundo.concesionaria.dao;

import com.automundo.concesionaria.model.PedidoItem;
import java.util.List;

public interface PedidoItemDAO {

    List<PedidoItem> get();

    PedidoItem get(Long id);

    void save(PedidoItem producto);

    void update(PedidoItem producto);

    void delete(Long id);
    
    List<PedidoItem> getByIdPedido(Long id);
}
