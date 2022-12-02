package com.quimba.sistemaventa.ProyectoIntegrador.util.reportes;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Cliente;

import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ClienteExporterPDF {

    private List<Cliente> listaClientes;

    public ClienteExporterPDF(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    private void cabeceraDeLaTabla(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.cyan);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.BLACK);

        celda.setPhrase(new Phrase("ID",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("NOMBRES",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("APELLIDO PATERNO",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("DNI",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("CELULAR",fuente));
        tabla.addCell(celda);
    }

    private void datosDeLaTabla(PdfPTable tabla){
        for (Cliente cliente: listaClientes) {
                tabla.addCell(String.valueOf(cliente.getId()));
                tabla.addCell(cliente.getNombre());
                tabla.addCell(cliente.getApellidoPaterno());
                tabla.addCell(cliente.getDni());
                tabla.addCell(cliente.getCelular());
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLACK);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de clientes");
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setFont(fuente);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f,2.3f,3f,3f,3f}); //ancho de las column
        tabla.setWidthPercentage(110);

        cabeceraDeLaTabla(tabla);
        datosDeLaTabla(tabla);

        documento.add(tabla);

        documento.close();
    }
}
