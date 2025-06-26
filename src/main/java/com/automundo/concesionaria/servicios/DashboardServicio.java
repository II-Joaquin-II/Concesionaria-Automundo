package com.automundo.concesionaria.servicios;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.repositorio.PedidoItemRepositorio;
import com.automundo.concesionaria.repositorio.PedidoRepositorio;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class DashboardServicio {

    @Autowired
    private PedidoRepositorio repo_pedido;

    public long contarPedidos() {
        return repo_pedido.count();
    }

    public BigDecimal totalVentas() {
        return repo_pedido.totalVentas();
    }

    public long contarPorEstado(String estado) {
        return repo_pedido.countByEstado(estado);
    }

    public List<Pedido> ultimosPedidos(int cantidad) {
        Pageable pageable = PageRequest.of(0, cantidad);
        return repo_pedido.findAllByOrderByFechaDesc(pageable);
    }

    @Autowired
    private PedidoItemRepositorio repo_pedido_item;

    public Map<String, Long> obtenerAccesoriosVendidos() {
        List<Object[]> resultados = repo_pedido_item.contarAccesoriosVendidos();
        Map<String, Long> accesoriosVendidos = new LinkedHashMap<>();
        for (Object[] fila : resultados) {
            String nombre = (String) fila[0];
            Long cantidad = (Long) fila[1];
            accesoriosVendidos.put(nombre, cantidad);
        }
        return accesoriosVendidos;
    }

}
