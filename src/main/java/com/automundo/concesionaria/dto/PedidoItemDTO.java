package com.automundo.concesionaria.dto;

import com.automundo.concesionaria.model.Accesorio;
import com.automundo.concesionaria.model.Pedido;
import java.math.BigDecimal;

public class PedidoItemDTO {

    private Long id_item;
    private Pedido pedido;
    private Accesorio accesorio;
    private String coloracc;
    private BigDecimal precioUnitario;

    public Long getId_item() {
        return id_item;
    }

    public void setId_item(Long id_item) {
        this.id_item = id_item;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Accesorio getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(Accesorio accesorio) {
        this.accesorio = accesorio;
    }

    public String getColoracc() {
        return coloracc;
    }

    public void setColoracc(String coloracc) {
        this.coloracc = coloracc;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

}
