package com.automundo.concesionaria.dao;

import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.model.PedidoItem;
import com.automundo.concesionaria.repositorio.PedidoItemRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoItemDAOImpl implements PedidoItemDAO{

    @Autowired
    private PedidoItemRepositorio pedidoitemRepositorio;

    @Override
    public List<PedidoItem> get() {
        return pedidoitemRepositorio.findAll();
    }

    @Override
    public PedidoItem get(Long id) {
        return pedidoitemRepositorio.findById(id).orElse(null);
    }

    @Override
    public void save(PedidoItem producto) {
        pedidoitemRepositorio.save(producto);
    }

    @Override
    public void update(PedidoItem producto) {
        pedidoitemRepositorio.save(producto);
    }

    @Override
    public void delete(Long id) {
        pedidoitemRepositorio.deleteById(id);
    }

    @Override
    public List<PedidoItem> getByIdPedido(Long id) {
        return pedidoitemRepositorio.findPedidoById(id);
    }
}
