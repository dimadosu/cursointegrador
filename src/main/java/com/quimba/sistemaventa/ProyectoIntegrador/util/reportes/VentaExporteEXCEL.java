package com.quimba.sistemaventa.ProyectoIntegrador.util.reportes;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VentaExporteEXCEL {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Venta> listaVentas;

    public VentaExporteEXCEL(List<Venta> listaVentas){
        this.listaVentas = listaVentas;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Ventas");
    }

    private void cabeceraTabla(){
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("N° de venta");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Usuario");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Cliente");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("fecha");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Importe Total");
        celda.setCellStyle(estilo);
    }

    private  void datosDeLaTabla(){
        int numeroFilas=1;
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for (Venta venta: listaVentas) {
            Row fila = hoja.createRow(numeroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(String.valueOf(venta.getId()));
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(venta.getUsuario().getNombre() + venta.getUsuario().getApellidoPaterno());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(venta.getCliente().getNombre() + venta.getCliente().getApellidoPaterno());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(String.valueOf(venta.getFecha()));
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(String.valueOf(venta.getTotal()));
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);
        }
    }

    public void exportar(HttpServletResponse response)throws IOException {
        cabeceraTabla();
        datosDeLaTabla();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);

        libro.close();
        outputStream.close();
    }
}
