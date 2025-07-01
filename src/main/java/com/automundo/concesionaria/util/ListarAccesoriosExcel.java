package com.automundo.concesionaria.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.automundo.concesionaria.model.Accesorio;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("adminveraccesorios.xlsx")
public class ListarAccesoriosExcel extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=listado-accesorios.xlsx");
        Sheet hoja = workbook.createSheet("Accesorios");

        // Cargar logo desde recursos
        InputStream imagenStream = new ClassPathResource("static/img/logo.jpg").getInputStream();
        byte[] bytes = IOUtils.toByteArray(imagenStream);
        int imagenIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        imagenStream.close();

        Drawing<?> dibujo = hoja.createDrawingPatriarch();
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
        anchor.setCol1(0);
        anchor.setRow1(0);
        Picture pict = dibujo.createPicture(anchor, imagenIndex);
        pict.resize();

        // Título
        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(5);
        celda.setCellValue("LISTADO GENERAL DE ACCESORIOS");

        // Encabezados (sin Imagen ni Acciones)
        Row filaData = hoja.createRow(2);
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio"};

        for (int i = 0; i < columnas.length; i++) {
            celda = filaData.createCell(i + 1);
            celda.setCellValue(columnas[i]);
        }

        @SuppressWarnings("unchecked")
        List<Accesorio> listaAccesorios = (List<Accesorio>) model.get("accesorios");
        int numFila = 3;
        for (Accesorio acc : listaAccesorios) {
            filaData = hoja.createRow(numFila++);

            filaData.createCell(1).setCellValue(acc.getId_acc());
            filaData.createCell(2).setCellValue(acc.getNombre());
            filaData.createCell(3).setCellValue(acc.getDescripcion());
            filaData.createCell(4).setCellValue("S/. " + acc.getPrecio());
        }

        for (int i = 0; i <= 5; i++) {
            hoja.autoSizeColumn(i);
        }
    }
}