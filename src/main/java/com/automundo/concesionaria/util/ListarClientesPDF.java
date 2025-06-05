package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Usuario;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.springframework.stereotype.Component;
import java.io.OutputStream;
import java.util.List;

@Component
public class ListarClientesPDF {

    public void exportarPDF(List<Usuario> clientes, OutputStream outputStream) throws Exception {

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("LISTADO GENERAL DE CLIENTES").setBold().setFontSize(16));

        float[] columnWidths = {30, 70, 70, 70, 70, 70, 100, 70};
        Table table = new Table(columnWidths);

        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre")));
        table.addHeaderCell(new Cell().add(new Paragraph("Apellidos")));
        table.addHeaderCell(new Cell().add(new Paragraph("DNI")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fecha Nac")));
        table.addHeaderCell(new Cell().add(new Paragraph("Celular")));
        table.addHeaderCell(new Cell().add(new Paragraph("Email")));
        table.addHeaderCell(new Cell().add(new Paragraph("Usuario")));

        for (Usuario u : clientes) {
            table.addCell(String.valueOf(u.getId_usuario()));
            table.addCell(u.getNombre_usuario());
            table.addCell(u.getApellidos_usuario());
            table.addCell(u.getDni());
            table.addCell(u.getFecha_nac().toString());
            table.addCell(u.getCelular());
            table.addCell(u.getEmail());
            table.addCell(u.getUsuario());
        }

        document.add(table);
        document.close();
    }
}
