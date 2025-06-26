package com.automundo.concesionaria.dao;

import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoDAOImpl implements PedidoDAO {
    
    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Override
    public List<Pedido> get() {
        return pedidoRepositorio.findAll();
    }

    @Override
    public Pedido get(Long id) {
        return pedidoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void save(Pedido producto) {
        pedidoRepositorio.save(producto);
    }

    @Override
    public void update(Pedido producto) {
        pedidoRepositorio.save(producto);
    }

    @Override
    public void delete(Long id) {
        pedidoRepositorio.deleteById(id);
    }

    @Override
    public List<Pedido> getByIdPedido(Long id) {
        return pedidoRepositorio.findPedidoById(id);
    }

    
}
