package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Accesorio;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.borders.Border;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

@Component
public class ListarAccesoriosPDF {

    public void exportarPDF(List<Accesorio> accesorios, OutputStream outputStream) throws Exception {

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Cargar logo
        String imagePath = "src/main/resources/static/img/logo.jpg";
        Image img = new Image(ImageDataFactory.create(imagePath));
        img.setOpacity(0.3f);
        img.scaleToFit(120, 60);  // Ajusta tamaño para que no ocupe mucho espacio

        // Posicionar logo arriba a la izquierda
        img.setFixedPosition(40, pdfDoc.getDefaultPageSize().getTop() - 80);
        document.add(img);

        // Añadir espacio para que el título no se monte con el logo
        document.add(new Paragraph("\n\n\n"));

        // Título centrado
        Paragraph titulo = new Paragraph("LISTADO GENERAL DE ACCESORIOS")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(titulo);

        document.add(new Paragraph("\n")); // espacio después del título

        // Definir tabla con 4 columnas
        float[] columnWidths = {50f, 150f, 250f, 70f};
        Table table = new Table(UnitValue.createPointArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Estilo para encabezados
        Cell headerCellStyle = new Cell()
                .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(new SolidBorder(1));

        // Encabezados
        table.addHeaderCell(new Cell().add(new Paragraph("ID")).setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre")).setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Descripción")).setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Precio")).setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY).setBold().setTextAlignment(TextAlignment.CENTER));

        // Datos
        for (Accesorio acc : accesorios) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(acc.getId_acc()))));
            table.addCell(new Cell().add(new Paragraph(acc.getNombre())));
            table.addCell(new Cell().add(new Paragraph(acc.getDescripcion())));
            table.addCell(new Cell().add(new Paragraph(String.format("S/. %.2f", acc.getPrecio()))).setTextAlignment(TextAlignment.RIGHT));
        }

        document.add(table);
        document.close();
    }
}
