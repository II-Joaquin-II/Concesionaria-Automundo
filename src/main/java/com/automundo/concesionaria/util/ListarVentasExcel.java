package com.automundo.concesionaria.util;

import com.automundo.concesionaria.model.Pedido;        // lista «ventas»
import com.automundo.concesionaria.model.PedidoItem;    // lista «ventas2»
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

@Component("adminventaauto.xlsx")          // ⬅ nombre lógico de la vista
public class ListarVentasExcel extends AbstractXlsxView {

    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        /* ─────────────  1) Config. cabecera HTTP ───────────── */
        response.setHeader("Content-Disposition",
                "attachment; filename=\"ventas-autos.xlsx\"");

        /* ─────────────  2) Logo (una sola vez) ───────────── */
        byte[] logoBytes;
        try (InputStream logoStream =
                     new ClassPathResource("static/img/logo.jpg").getInputStream()) {
            logoBytes = IOUtils.toByteArray(logoStream);
        }
        int logoIndex = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG);

        CreationHelper helper = workbook.getCreationHelper();

        /**********************************************************************
         *  HOJA 1  ➜  Ventas Autos
         *********************************************************************/
        Sheet hojaVentas = workbook.createSheet("Ventas Autos");

        // Logo
        Drawing<?> drawing = hojaVentas.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(0);  // Columna A
        anchor.setRow1(0);  // Fila 1
        Picture pict = drawing.createPicture(anchor, logoIndex);
        pict.resize();

        // Título
        Row filaTitulo = hojaVentas.createRow(0);
        Cell celTitulo = filaTitulo.createCell(5);
        celTitulo.setCellValue("LISTADO GENERAL DE VENTAS");

        // Encabezados
        String[] columnasVentas = { "ID Pedido", "ID Cliente", "Nombre completo",
                                    "ID Auto", "Auto", "Color",
                                    "Fecha", "Total (USD)", "Estado" };

        Row filaCab = hojaVentas.createRow(2);
        for (int i = 0; i < columnasVentas.length; i++) {
            filaCab.createCell(i + 1).setCellValue(columnasVentas[i]);
        }

        List<Pedido> ventas = (List<Pedido>) model.get("ventas");
        int numFila = 3;

        for (Pedido venta : ventas) {
            Row fila = hojaVentas.createRow(numFila++);

            fila.createCell(1).setCellValue(venta.getId_pedido());
            fila.createCell(2).setCellValue(venta.getUsuario().getId_usuario());
            fila.createCell(3).setCellValue(
                   venta.getUsuario().getNombre_usuario() + " " +
                   venta.getUsuario().getApellidos_usuario());

            fila.createCell(4).setCellValue(venta.getAutos().getIdAuto());
            fila.createCell(5).setCellValue(
                   venta.getAutos().getModelo() + " " +
                   venta.getAutos().getMarca()  + " " +
                   venta.getAutos().getAno());

            fila.createCell(6).setCellValue(venta.getColorauto());
            fila.createCell(7).setCellValue(
                   venta.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            fila.createCell(8).setCellValue(venta.getTotal().doubleValue());
            fila.createCell(9).setCellValue(venta.getEstado());
        }

        // Ajustar ancho
        for (int i = 1; i <= 9; i++) {
            hojaVentas.autoSizeColumn(i);
        }

        /**********************************************************************
         *  HOJA 2  ➜  Accesorios Vendidos
         *********************************************************************/
        Sheet hojaAcc = workbook.createSheet("Accesorios Vendidos");

        // Reutilizamos logo
        Drawing<?> drawing2 = hojaAcc.createDrawingPatriarch();
        ClientAnchor anchor2 = helper.createClientAnchor();
        anchor2.setCol1(0);
        anchor2.setRow1(0);
        drawing2.createPicture(anchor2, logoIndex).resize();

        Row filaTitulo2 = hojaAcc.createRow(0);
        filaTitulo2.createCell(4).setCellValue("LISTADO DE ACCESORIOS VENDIDOS");

        String[] columnasAcc = { "ID Pedido", "ID Accesorio",
                                 "Accesorio", "Color", "Precio Unitario (USD)" };

        Row filaCab2 = hojaAcc.createRow(2);
        for (int i = 0; i < columnasAcc.length; i++) {
            filaCab2.createCell(i + 1).setCellValue(columnasAcc[i]);
        }

        List<PedidoItem> ventas2 = (List<PedidoItem>) model.get("ventas2");
        int numFila2 = 3;

        for (PedidoItem item : ventas2) {
            Row fila = hojaAcc.createRow(numFila2++);

            fila.createCell(1).setCellValue(item.getPedido().getId_pedido());
            fila.createCell(2).setCellValue(item.getAccesorio().getId_acc());
            fila.createCell(3).setCellValue(item.getAccesorio().getNombre());
            fila.createCell(4).setCellValue(item.getColoracc());
            fila.createCell(5).setCellValue(item.getPrecioUnitario().doubleValue());
        }

        for (int i = 1; i <= 5; i++) {
            hojaAcc.autoSizeColumn(i);
        }
    }
}