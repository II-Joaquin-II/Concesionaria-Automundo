package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.dao.PedidoItemDAO;
import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.model.PedidoItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoItemServicioImpl implements PedidoItemServicio {

    @Autowired
    private PedidoItemDAO pedidoItemDAO;

    @Transactional
    @Override
    public List<PedidoItem> get() {
        return pedidoItemDAO.get();
    }

    @Transactional
    @Override
    public PedidoItem get(Long id) {
        return pedidoItemDAO.get(id);
    }

    @Transactional
    @Override
    public void save(PedidoItem producto) {
        pedidoItemDAO.save(producto);
    }

    @Transactional
    @Override
    public void update(PedidoItem producto) {
        pedidoItemDAO.update(producto);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        pedidoItemDAO.delete(id);
    }

    @Transactional
    @Override
    public List<PedidoItem> getByIdPedido(Long id) {
        return pedidoItemDAO.getByIdPedido(id);
    }
}
