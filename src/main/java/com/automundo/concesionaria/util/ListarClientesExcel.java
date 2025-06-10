package com.automundo.concesionaria.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import com.automundo.concesionaria.model.Usuario;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import org.apache.poi.ss.usermodel.Row;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("adminverclientes.xlsx")
public class ListarClientesExcel extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=listado-clientes.xlsx");
        Sheet hoja = workbook.createSheet("Clientes");

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

        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(5);
        celda.setCellValue("LISTADO GENERAL DE CLIENTES");

        Row filaData = hoja.createRow(2);
        String[] Columnas = {"ID", "Nombre", "Apellidos", "DNI", "Fecha Nacimiento", "Celular", "Email", "Usuario"};

        for (int i = 0; i < Columnas.length; i++) {
            celda = filaData.createCell(i + 1);
            celda.setCellValue(Columnas[i]);
        }

        List<Usuario> listaCliente = (List<Usuario>) model.get("clientes");
        int numFila = 3;
        for (Usuario cliente : listaCliente) {

            boolean esAdmin = cliente.getRoles().stream()
                    .anyMatch(rol -> rol.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));

            if (esAdmin) {
                continue;
            }

            filaData = hoja.createRow(numFila);

            filaData.createCell(1).setCellValue(cliente.getId_usuario());
            filaData.createCell(2).setCellValue(cliente.getNombre_usuario());
            filaData.createCell(3).setCellValue(cliente.getApellidos_usuario());
            filaData.createCell(4).setCellValue(cliente.getDni());
            filaData.createCell(5).setCellValue(cliente.getFecha_nac().toString());
            filaData.createCell(6).setCellValue(cliente.getCelular());
            filaData.createCell(7).setCellValue(cliente.getEmail());
            filaData.createCell(8).setCellValue(cliente.getUsuario());

            numFila++;
        }

        for (int i = 0; i <= 8; i++) {
            hoja.autoSizeColumn(i);
        }
    }

}
