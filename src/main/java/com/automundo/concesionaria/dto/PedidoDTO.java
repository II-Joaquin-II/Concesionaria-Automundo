package com.automundo.concesionaria.dto;

import com.automundo.concesionaria.model.Autos;
import com.automundo.concesionaria.model.PedidoItem;
import com.automundo.concesionaria.model.Usuario;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    private Long id_pedido;
    private Usuario usuario;
    private Autos autos;
    private String colorauto;
    private LocalDateTime fecha = LocalDateTime.now();
    private BigDecimal total;
    private List<PedidoItem> items;

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Autos getAutos() {
        return autos;
    }

    public void setAutos(Autos autos) {
        this.autos = autos;
    }

    public String getColorauto() {
        return colorauto;
    }

    public void setColorauto(String colorauto) {
        this.colorauto = colorauto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<PedidoItem> getItems() {
        return items;
    }

    public void setItems(List<PedidoItem> items) {
        this.items = items;
    }
}
