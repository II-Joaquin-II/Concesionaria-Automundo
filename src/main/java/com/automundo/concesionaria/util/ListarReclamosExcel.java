package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Reclamo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

@Component("adminverreclamos.xlsx")
public class ListarReclamosExcel extends AbstractXlsxView {
//Formato en el que se crea excel
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"listado-reclamos.xlsx\"");
        Sheet hoja = workbook.createSheet("Reclamos");

        //Inicio del titulo, Fila 1 en excel
        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(0);
        celda.setCellValue("LISTADO GENERAL DE RECLAMOS");

        //Inicio de la informacion, Fila 3 en excel
        Row filaData = hoja.createRow(2);
        String[] Columnas = {"ID", "ID Usuario", "Fecha Incidente", "Motivo del Reclamo", "Tipo de Vehiculo", "Detalle", "Estado", "Fecha del Reclamo"};

        for (int i = 0; i < Columnas.length; i++) {
            celda = filaData.createCell(i+1);
            celda.setCellValue(Columnas[i]);
        }

        List<Reclamo> listaR = (List<Reclamo>) model.get("reclamos");
        int numFila = 3;
        for (Reclamo reclamo : listaR) {
            filaData = hoja.createRow(numFila);

            filaData.createCell(1).setCellValue(reclamo.getIdReclamo());
            filaData.createCell(2).setCellValue(reclamo.getIdCliente().getId_usuario());
            filaData.createCell(3).setCellValue(reclamo.getFechaincidente().toString());
            filaData.createCell(4).setCellValue(reclamo.getMotivoReclamo());
            filaData.createCell(5).setCellValue(reclamo.getTipo_Vehiculo());
            filaData.createCell(6).setCellValue(reclamo.getDetalle());
            filaData.createCell(7).setCellValue(reclamo.getEstadoReclamo());
            filaData.createCell(8).setCellValue(reclamo.getFechaReclamo().toString());

            numFila++;
        }
        //Ajuste de la celda al tamaño del texto
        for (int i = 0; i <= 8; i++) {
            hoja.autoSizeColumn(i);
        }
    }

}
