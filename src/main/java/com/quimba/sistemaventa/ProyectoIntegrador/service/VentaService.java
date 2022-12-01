package com.quimba.sistemaventa.ProyectoIntegrador.service;

import com.quimba.sistemaventa.ProyectoIntegrador.modelo.Venta;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.DetalleVentaRepository;
import com.quimba.sistemaventa.ProyectoIntegrador.repository.VentaRepository;
import com.sun.istack.NotNull;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<Venta> list(){
        return ventaRepository.findAll();
    }

    public Optional<Venta> getOne(Integer id){
        return  ventaRepository.findById(id);
    }

    public void save(Venta venta){
        ventaRepository.save(venta);
    }

    public void delete(){
        ventaRepository.deleteAll();
    }

    public Integer findById(){
        return ventaRepository.findById();
    }


    /*
    @NotNull
    public ResponseEntity<Resource> exportReporte(Integer idClie, Integer idVenta) {
        Optional<Venta> optVenta = this.ventaRepository.findByIdAndClienteId(idClie,idVenta);
        Double total = optVenta.get().getTotal();
        if(optVenta.isPresent()){
            try{
                final Venta venta = optVenta.get();
                final File file = ResourceUtils.getFile("classpath:ReporteDeVenta.jasper");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);

                final HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("nombreCliente", venta.getCliente().getnombreCliente());
                parameters.put("total", venta.getTotal());
                parameters.put("id", venta.getId());
                parameters.put("ds", new JRBeanCollectionDataSource((Collection<?>) this.detalleVentaRepository.findByVenta(idVenta)));

                JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters, new JREmptyDataSource());
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                StringBuilder stringBuilder = new StringBuilder().append("ReportePdf:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder.append(venta.getId())
                                .append("generateDate:").append(sdf).append(".pdf").toString()).build();
                HttpHeaders  headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));

            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            return ResponseEntity.noContent().build(); //no se encontro el reporte
        }
        return null;
    }

     */
}
