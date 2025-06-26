package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dao.PedidoDAO;
import com.automundo.concesionaria.model.Pedido;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoServicioImpl implements PedidoServicio {
    
    @Autowired
    private PedidoDAO pedidoDAO;

    @Transactional
    @Override
    public List<Pedido> get() {
        return pedidoDAO.get();
    }

    @Transactional
    @Override
    public Pedido get(Long id) {
        return pedidoDAO.get(id);
    }

    @Transactional
    @Override
    public void save(Pedido producto) {
        pedidoDAO.save(producto);
    }

    @Transactional
    @Override
    public void update(Pedido producto) {
        pedidoDAO.update(producto);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        pedidoDAO.delete(id);
    }

    @Transactional
    @Override
    public List<Pedido> getByIdPedido(Long id) {
        return pedidoDAO.getByIdPedido(id);
    }
}
