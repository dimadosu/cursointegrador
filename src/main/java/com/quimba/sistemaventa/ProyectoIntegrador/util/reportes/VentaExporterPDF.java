package com.quimba.sistemaventa.ProyectoIntegrador.util.reportes;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.DetalleVenta;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Empresa;
import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import com.quimba.sistemaventa.ProyectoIntegrador.service.EmailService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.EmpresaService;
import com.quimba.sistemaventa.ProyectoIntegrador.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VentaExporterPDF {


    @Autowired
    private VentaService ventaService;

    @Autowired
    private EmpresaService empresaService;

    private List<DetalleVenta> detalleVentaList;
    private  Integer id;

    Venta venta;

    //static Integer idEmpresa=1;

    public VentaExporterPDF(List<DetalleVenta> detalleVentaList, Integer id, Venta venta){
        this.detalleVentaList = detalleVentaList;
        this.id = id;
        this.venta = venta;
    }

    private  void cabeceraDeLaTabla(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.cyan);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.BLACK);

        celda.setPhrase(new Phrase("Descripcion",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Cantidad",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Precio.Unit",fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Total",fuente));
        tabla.addCell(celda);

    }

    private void cabeceraDatosCliente(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.BLACK);

        celda.setPhrase(new Phrase("Nombre",fuente));
        celda.setBorder(0);
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Apellido Paterno",fuente));
        celda.setBorder(0);
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Apellido Materno",fuente));
        celda.setBorder(0);
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Codigo",fuente));
        celda.setBorder(0);
        tabla.addCell(celda);

    }
    private void datosDeLaTabla(PdfPTable tabla){
        for (DetalleVenta detalleVenta: detalleVentaList){
            tabla.addCell(detalleVenta.getProducto().getNombre());
            tabla.addCell(detalleVenta.getCantidad().toString());
            tabla.addCell(detalleVenta.getPrecio().toString());
            tabla.addCell(detalleVenta.getImporte().toString());
        }
    }

    private  void datosDelCliente(PdfPTable tabla){
            tabla.addCell(detalleVentaList.get(1).getVenta().getCliente().getNombre());
            tabla.addCell((detalleVentaList.get(1).getVenta().getCliente().getApellidoPaterno()));
            tabla.addCell((detalleVentaList.get(1).getVenta().getCliente().getApellidoMaterno()));
            tabla.addCell((detalleVentaList.get(1).getVenta().getCliente().getId().toString()));

    }

    public void exportar(HttpServletResponse response) throws IOException{
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLACK);
        fuente.setSize(18);

        Paragraph fecha = new Paragraph();
        Date date = new Date();
        fecha.add("Venta: " + venta.getId() + "\n" + "Fecha de Emisión: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

        //tabla para datos de la empresa
        PdfPTable encabezado = new PdfPTable(4);
        encabezado.setWidthPercentage(100);
        encabezado.getDefaultCell().setBorder(0); //quitamos border

        //tamaño para cada celda
        float [] columEnca = new float[]{20f,30f,70f,40f};
        encabezado.setWidths(columEnca);
        encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

        encabezado.addCell("");
        encabezado.addCell("Ruc: " + "10437910656" + "\nNombre: " + "Cevicheria La Quimba" + "\nTelefono:" +
                "992 646 641" + "\nDireccion: " + "Av. J.J Elias 487 11001 Ica, Perú");
        encabezado.addCell(fecha);

        documento.add(encabezado);

        PdfPTable tablaCliente = new PdfPTable(4);
        tablaCliente.setWidthPercentage(100);
        tablaCliente.getDefaultCell().setBorder(0);
        tablaCliente.setWidths(new float[]{20f,50f,30f,40f});
        tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);

        cabeceraDatosCliente(tablaCliente);
        datosDelCliente(tablaCliente);
        documento.add(tablaCliente);

        //Titulo
        Paragraph titulo = new Paragraph("Comprobante de compra" + "\n\n");
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setFont(fuente);
        documento.add(titulo);

        //Detalle del pedido
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3f,2.5f,3f,3f});
        tabla.getDefaultCell().setBorder(0);

        cabeceraDeLaTabla(tabla);
        datosDeLaTabla(tabla);
        documento.add(tabla);

        //costo final
        Paragraph info = new Paragraph();
        info.add(Chunk.NEWLINE);
        info.add(" " + "Total a pagar:" + "s/" + venta.getTotal() + "\n\n");
        info.setAlignment(Element.ALIGN_RIGHT);
        documento.add(info);

        //mensaje
        Paragraph mensaje = new Paragraph();
        mensaje.add(Chunk.NEWLINE);
        mensaje.add("Gracias por la compra");
        mensaje.setAlignment(Element.ALIGN_CENTER);
        documento.add(mensaje);

        documento.close();
    }

    /*
    public void exportarPdfVenta(){
        try{

            //Integer id = ventaService.findById(); //obtenemos la utima venta registrada
            FileOutputStream archivo; //ruta de salida
            File file = new File("C:\\Users\\dijes\\Documents\\REPORTESQUIMBA/venta"+ id + ".pdf");
            archivo = new FileOutputStream(file);

            //creando el documento
            Document doc = new Document();
            PdfWriter.getInstance(doc,archivo);
            doc.open();

            Paragraph fecha = new Paragraph();

            Font negrita = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, Color.DARK_GRAY);
            fecha.add(Chunk.NEWLINE);

            Date date = new Date();
            fecha.add("Venta: " + id + "\n" + "Fecha de Emisión: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            //tabla para la boleta
            PdfPTable encabezado = new PdfPTable(4);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0); //quitamos border

            //tamaño para cada celda
            float [] columEnca = new float[]{20f,30f,70f,40f};
            encabezado.setWidths(columEnca);
            encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);


           // Empresa empresa = empresaService.getOne(idEmpresa).get();

            encabezado.addCell("");
            encabezado.addCell("Ruc: " + "10437910656" + "\nNombre: " + "Cevicheria La Quimba" + "\nTelefono:" +
                    "992 646 641" + "\nDireccion: " + "Av. J.J Elias 487 11001 Ica, Perú");
            encabezado.addCell(fecha);

            doc.add(encabezado);

            //DATOS DEL CLIENTE
            Paragraph clien = new Paragraph();
            clien.add(Chunk.NEWLINE);
            clien.add("Datos del cliene" + "\n\n");
            doc.add(clien);

            PdfPTable tabla_clie = new PdfPTable(4);
            tabla_clie.setWidthPercentage(100);
            tabla_clie.getDefaultCell().setBorder(0);
            float[] columna_clie = new float[]{20f,50f,30f,40f};
            tabla_clie.setWidths(columna_clie);
            tabla_clie.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Dni", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombres", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("Apellidos", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("Codigo", negrita));

            //quitamos los bordes
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            tabla_clie.addCell(cl1);
            tabla_clie.addCell(cl2);
            tabla_clie.addCell(cl3);
            tabla_clie.addCell(cl4);

            tabla_clie.addCell(venta.getCliente().getDni());
            tabla_clie.addCell(venta.getCliente().getNombre());
            tabla_clie.addCell(venta.getCliente().getApellidoPaterno() + " " + venta.getCliente().getApellidoMaterno());
            tabla_clie.addCell(venta.getCliente().getId().toString());

            doc.add(tabla_clie);

            //PRODUCTOS
            PdfPTable tablePedido = new PdfPTable(4);
            tablePedido.setWidthPercentage(100);
            tablePedido.getDefaultCell().setBorder(0); // quitamos border
            float[] columcur = new float[]{50f, 10f, 15f, 20f};
            tablePedido.setWidths(columcur);
            tablePedido.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pcel1 = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell pcel2 = new PdfPCell(new Phrase("Cantidad", negrita));
            PdfPCell pcel3 = new PdfPCell(new Phrase("Precio U", negrita));
            PdfPCell pcel4 = new PdfPCell(new Phrase("Total", negrita));

            //quitamos border
            pcel1.setBorder(0);
            pcel2.setBorder(0);
            pcel3.setBorder(0);
            pcel4.setBorder(0);

            //color de fondo
            pcel1.setBackgroundColor(Color.LIGHT_GRAY);
            pcel2.setBackgroundColor(Color.LIGHT_GRAY);
            pcel3.setBackgroundColor(Color.LIGHT_GRAY);
            pcel4.setBackgroundColor(Color.LIGHT_GRAY);

            tabla_clie.addCell(pcel1);
            tabla_clie.addCell(pcel2);
            tabla_clie.addCell(pcel3);
            tabla_clie.addCell(pcel4);

            //bucle para recorrer toda la tabla
            for (DetalleVenta detalleVenta: detalleVentaList){
                String platillo = detalleVenta.getNombre();
                String cantidad = detalleVenta.getCantidad().toString();
                String preUni = detalleVenta.getPrecio().toString();
                String importe = detalleVenta.getImporte().toString();

                tabla_clie.addCell(platillo);
                tabla_clie.addCell(cantidad);
                tabla_clie.addCell(preUni);
                tabla_clie.addCell(importe);
            }

            doc.add(tabla_clie);

            //costo final
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add(" " + "Total a pagar:" + "s/" + venta.getTotal() + "\n\n");
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            //mensaje
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por la compra");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);

        }catch (FileNotFoundException | DocumentException e){
            System.out.println(e);
        }catch (IOException ex){
            System.out.println(ex);
        }

    }*/
}
