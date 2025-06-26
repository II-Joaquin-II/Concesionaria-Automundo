package com.automundo.concesionaria.servicios;

import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    public void guardarPedido(Pedido pedido) {
        pedidoRepositorio.save(pedido);
    }
}
