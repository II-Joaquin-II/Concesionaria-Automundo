package com.automundo.concesionaria.dao;

import com.automundo.concesionaria.model.Pedido;
import java.util.List;

public interface PedidoDAO {
    List<Pedido> get();

    Pedido get(Long id);

    void save(Pedido producto);

    void update(Pedido producto);

    void delete(Long id);

    List<Pedido> getByIdPedido(Long id);
    /*List<Pedido> get();*/
}
