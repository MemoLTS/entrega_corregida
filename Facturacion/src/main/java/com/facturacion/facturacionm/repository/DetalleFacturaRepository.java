package com.facturacion.facturacionm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.facturacionm.model.DetalleFactura;
import com.facturacion.facturacionm.model.Factura;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

    List<DetalleFactura> findByFactura(Factura factura);

}
