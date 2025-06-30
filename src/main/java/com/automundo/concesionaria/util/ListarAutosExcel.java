package com.automundo.concesionaria.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.automundo.concesionaria.model.Autos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.util.CellRangeAddress;

@Component("adminverautos.xlsx")
public class ListarAutosExcel extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=listado-autos.xlsx");
        Sheet hoja = workbook.createSheet("Autos");

        // Logo (igual que en clientes)
        InputStream imagenStream = new ClassPathResource("static/img/logo.jpg").getInputStream();
        byte[] bytes = IOUtils.toByteArray(imagenStream);
        int imagenIndex = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        imagenStream.close();

        Drawing<?> dibujo = hoja.createDrawingPatriarch();
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
        anchor.setCol1(0);
        anchor.setRow1(0);
        Picture pict = dibujo.createPicture(anchor, imagenIndex);
        pict.resize();

        // Título
       Row filaTitulo = hoja.createRow(0);

        // Crear celda combinada de la columna E (índice 4) a la M (índice 12)
        hoja.addMergedRegion(new CellRangeAddress(0, 0, 4, 12));

        // Crear la celda en la columna E (la primera de la combinación)
        Cell celda = filaTitulo.createCell(4);
        celda.setCellValue("LISTADO GENERAL DE AUTOS");

        // Aplicar estilo centrado y en negrita
        CellStyle estiloTitulo = workbook.createCellStyle();
        Font fuenteTitulo = workbook.createFont();
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeightInPoints((short) 14);
        estiloTitulo.setFont(fuenteTitulo);
        estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
        estiloTitulo.setVerticalAlignment(VerticalAlignment.CENTER);

        celda.setCellStyle(estiloTitulo);


        // Encabezados
        Row filaData = hoja.createRow(2);
        String[] columnas = {"ID", "Modelo", "Marca", "Año", "Precio", "Kilometraje", "Transmisión", "Combustible", "Equipamiento1", "Equipamiento2", "Equipamiento3", "Equipamiento4", "Categoría", "Estado"};

        for (int i = 0; i < columnas.length; i++) {
            celda = filaData.createCell(i + 1);
            celda.setCellValue(columnas[i]);
        }

        // Lista de autos del modelo (deberás pasar la lista con key "autos" al modelo)
        List<Autos> listaAutos = (List<Autos>) model.get("autos");

        int numFila = 3;
        for (Autos auto : listaAutos) {
            filaData = hoja.createRow(numFila);

            filaData.createCell(1).setCellValue(auto.getIdAuto());
            filaData.createCell(2).setCellValue(auto.getModelo());
            filaData.createCell(3).setCellValue(auto.getMarca());
            filaData.createCell(4).setCellValue(auto.getAno() != null ? auto.getAno() : 0);
            filaData.createCell(5).setCellValue(auto.getPrecio() != null ? auto.getPrecio().doubleValue() : 0);
            filaData.createCell(6).setCellValue(auto.getKilometraje() != null ? auto.getKilometraje() : 0);
            filaData.createCell(7).setCellValue(auto.getTransmision());
            filaData.createCell(8).setCellValue(auto.getCombustible());
            filaData.createCell(9).setCellValue(auto.getEquipamiento1());
            filaData.createCell(10).setCellValue(auto.getEquipamiento2());
            filaData.createCell(11).setCellValue(auto.getEquipamiento3());
            filaData.createCell(12).setCellValue(auto.getEquipamiento4());
            filaData.createCell(13).setCellValue(auto.getCategoria());
            filaData.createCell(14).setCellValue(auto.getEstado());

            numFila++;
        }

        // Auto-ajustar ancho columnas
        for (int i = 1; i <= columnas.length; i++) {
            hoja.autoSizeColumn(i);
        }
    }
}