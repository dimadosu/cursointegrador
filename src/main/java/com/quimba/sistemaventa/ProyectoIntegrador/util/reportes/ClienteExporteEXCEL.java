package com.quimba.sistemaventa.ProyectoIntegrador.util.reportes;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Cliente;
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

public class ClienteExporteEXCEL {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Cliente> listaClietes;

    public ClienteExporteEXCEL( List<Cliente> listaClietes) {
        this.listaClietes = listaClietes;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Empleados");
    }

    private void cabeceraDeTabla(){
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("Id");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Nombres");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Apellido Paterno");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Dni");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Celular");
        celda.setCellStyle(estilo);
    }

    private void datosDeLaTabla(){
        int numeroFilas =1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for ( Cliente cliente: listaClietes) {
            Row fila = hoja.createRow(numeroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(String.valueOf(cliente.getId()));
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(cliente.getNombre());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(cliente.getApellidoPaterno());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(cliente.getDni());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(cliente.getCelular());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        cabeceraDeTabla();
        datosDeLaTabla();

        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);

        libro.close();
        outputStream.close();
    }
}
