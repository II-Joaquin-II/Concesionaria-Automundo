package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Pedido;
import com.automundo.concesionaria.model.PedidoItem;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ListarVentasPDF {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** Genera un PDF con dos tablas: Ventas Autos y Accesorios Vendidos */
    public void exportarPDF(List<Pedido> ventas,
                            List<PedidoItem> accesorios,
                            OutputStream out) throws Exception {

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addNewPage();
        Document doc = new Document(pdf);

        /* ───── Marca de agua / logo ───── */
        try (InputStream logoStream =
                     new ClassPathResource("static/img/logo.jpg").getInputStream()) {

            Image logo = new Image(ImageDataFactory.create(logoStream.readAllBytes()))
                            .setOpacity(0.15f)
                            .scaleToFit(300, 300);

            PdfPage page = pdf.getFirstPage();
            Rectangle pageSize = page.getPageSize();
            float x = (pageSize.getWidth()  - logo.getImageScaledWidth())  / 2;
            float y = (pageSize.getHeight() - logo.getImageScaledHeight()) / 2;
            logo.setFixedPosition(x, y);
            doc.add(logo);
        }

        /* ───── Título y tabla de ventas ───── */
        doc.add(new Paragraph("LISTADO GENERAL DE VENTAS")
                    .setBold().setFontSize(16).setMarginTop(10));

        float[] wVentas = {40, 60, 130, 40, 120, 45, 80, 60, 45};
        Table tv = new Table(wVentas).setFontSize(9);

        String[] headVentas = { "ID Ped", "ID Cliente", "Nombre completo",
                                "ID Auto", "Auto", "Color", "Fecha",
                                "Total USD", "Estado" };
        for (String h : headVentas) tv.addHeaderCell(new Cell().add(new Paragraph(h).setBold()));

        for (Pedido p : ventas) {
            tv.addCell(String.valueOf(p.getId_pedido()));
            tv.addCell(String.valueOf(p.getUsuario().getId_usuario()));
            tv.addCell(p.getUsuario().getNombre_usuario() + " " +
                       p.getUsuario().getApellidos_usuario());
            tv.addCell(String.valueOf(p.getAutos().getIdAuto()));
            tv.addCell(p.getAutos().getModelo() + " " +
                       p.getAutos().getMarca()  + " " +
                       p.getAutos().getAno());
            tv.addCell(p.getColorauto());
            tv.addCell(p.getFecha().format(FMT));
            tv.addCell(p.getTotal().toString());
            tv.addCell(p.getEstado());
        }
        doc.add(tv);

        /* ───── Salto de línea y tabla de accesorios ───── */
        doc.add(new Paragraph("\nACCESORIOS VENDIDOS")
                    .setBold().setFontSize(14));

        float[] wAcc = {40, 50, 130, 45, 60};
        Table ta = new Table(wAcc).setFontSize(9);

        String[] headAcc = { "ID Ped", "ID Acc", "Accesorio",
                             "Color", "Precio USD" };
        for (String h : headAcc) ta.addHeaderCell(new Cell().add(new Paragraph(h).setBold()));

        for (PedidoItem it : accesorios) {
            ta.addCell(String.valueOf(it.getPedido().getId_pedido()));
            ta.addCell(String.valueOf(it.getAccesorio().getId_acc()));
            ta.addCell(it.getAccesorio().getNombre());
            ta.addCell(it.getColoracc());
            ta.addCell(it.getPrecioUnitario().toString());
        }
        doc.add(ta);

        doc.close();
    }
}