package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Item;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Component
public class ResumenPedidoPDF {

    public void exportar(List<Item> items, double total, OutputStream os) throws Exception {

        PdfWriter   writer = new PdfWriter(os);
        PdfDocument pdf    = new PdfDocument(writer);
        Document    doc    = new Document(pdf, PageSize.A4);
        doc.setMargins(40, 40, 40, 40);

        /* ---------- Logo “marca de agua” ---------- */
        ClassPathResource logoRes = new ClassPathResource("static/img/logo.jpg");
        Image logo = new Image(ImageDataFactory.create(logoRes.getInputStream().readAllBytes()))
                         .setOpacity(0.25f)
                         .scaleToFit(300, 300);

        PdfPage   page = pdf.addNewPage();
        Rectangle ps   = page.getPageSize();
        float x = (ps.getWidth()  - logo.getImageScaledWidth())  / 2;
float y = (ps.getHeight() - logo.getImageScaledHeight()) / 2;
        logo.setFixedPosition(1,x,y);
        doc.add(logo);

        /* ---------- Cabecera ---------- */
        doc.add(new Paragraph("RESUMEN DE COMPRA")
                    .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
        doc.add(new Paragraph("Fecha: " + LocalDate.now())
                    .setTextAlignment(TextAlignment.RIGHT));

        /* ---------- Tabla de ítems ---------- */
        float[] widths = {30, 200, 100, 80};
        Table tabla = new Table(widths);
        tabla.setWidth(UnitValue.createPercentValue(100));

        tabla.addHeaderCell("N°");
        tabla.addHeaderCell("Producto");
        tabla.addHeaderCell("Color");
        tabla.addHeaderCell("Precio (S/.)");

        int i = 1;
        for (Item it : items) {
            tabla.addCell(String.valueOf(i++));
            tabla.addCell(it.getNombre());
            tabla.addCell(it.getColor());
            tabla.addCell(String.format("%.2f", it.getPrecio()));
        }

        Cell totalCell = new Cell(1,3)
                .add(new Paragraph("Total a pagar:").setBold())
                .setTextAlignment(TextAlignment.RIGHT);
        tabla.addCell(totalCell);
        tabla.addCell(String.format("%.2f", total));

        doc.add(tabla);
        doc.close();
    }
}