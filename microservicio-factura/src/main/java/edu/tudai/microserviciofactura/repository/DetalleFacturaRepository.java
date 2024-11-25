package edu.tudai.microserviciofactura.repository;

import edu.tudai.microserviciofactura.entity.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
}
