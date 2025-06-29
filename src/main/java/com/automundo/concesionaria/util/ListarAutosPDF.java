package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Autos;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

@Component
public class ListarAutosPDF {

    public void exportarPDF(List<Autos> autos, OutputStream outputStream) throws Exception {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addNewPage();
        Document document = new Document(pdfDoc);

        // Logo
        String imagePath = "src/main/resources/static/img/logo.jpg";
        Image img = new Image(ImageDataFactory.create(imagePath));
        img.setOpacity(0.3f);
        img.scaleToFit(300, 300);

        PdfPage page = pdfDoc.getFirstPage();
        Rectangle pageSize = page.getPageSize();
        float x = (pageSize.getWidth() - img.getImageScaledWidth()) / 2;
        float y = (pageSize.getHeight() - img.getImageScaledHeight()) / 2;
        img.setFixedPosition(x, y);

        document.add(img);

        // Título
        document.add(new Paragraph("LISTADO GENERAL DE AUTOS").setBold().setFontSize(16));

        // Columnas (quitando Categoría y Estado)
        float[] columnWidths = {30, 70, 70, 40, 60, 60, 60, 60};
        Table table = new Table(columnWidths);

        // Encabezados
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Modelo")));
        table.addHeaderCell(new Cell().add(new Paragraph("Marca")));
        table.addHeaderCell(new Cell().add(new Paragraph("Año")));
        table.addHeaderCell(new Cell().add(new Paragraph("Precio")));
        table.addHeaderCell(new Cell().add(new Paragraph("Kilometraje")));
        table.addHeaderCell(new Cell().add(new Paragraph("Transmisión")));
        table.addHeaderCell(new Cell().add(new Paragraph("Combustible")));

        // Datos
        for (Autos auto : autos) {
            table.addCell(String.valueOf(auto.getIdAuto()));
            table.addCell(auto.getModelo());
            table.addCell(auto.getMarca());
            table.addCell(auto.getAno() != null ? auto.getAno().toString() : "-");
            table.addCell(auto.getPrecio() != null ? auto.getPrecio().toString() : "-");
            table.addCell(auto.getKilometraje() != null ? auto.getKilometraje().toString() : "-");
            table.addCell(auto.getTransmision());
            table.addCell(auto.getCombustible());
        }

        document.add(table);
        document.close();
    }
}