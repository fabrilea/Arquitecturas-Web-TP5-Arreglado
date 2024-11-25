package edu.tudai.microserviciofactura.service;

import edu.tudai.microserviciofactura.entity.Tarifa;
import edu.tudai.microserviciofactura.repository.TarifaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    @Transactional(readOnly = true)
    public List<Tarifa> findAll() {
        return tarifaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarifa findById(Long id) {
        return tarifaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Tarifa save(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    @Transactional
    public Tarifa update(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    @Transactional
    public void delete(Long id) {
        tarifaRepository.deleteById(id);
    }

    /*****************************************************/

    public void ajustarPrecios(double nuevaTarifaBase, double nuevaTarifaExtra, LocalDate fechaInicio) {
        Tarifa tarifaBase = new Tarifa(Tarifa.TipoTarifa.BASE, nuevaTarifaBase, fechaInicio, null);
        Tarifa tarifaExtra = new Tarifa(Tarifa.TipoTarifa.EXTRA_PAUSA, nuevaTarifaExtra, fechaInicio, null);
        tarifaRepository.save(tarifaBase);
        tarifaRepository.save(tarifaExtra);
    }
    //El metodo ajustarPrecios no necesita explícitamente @Transactional porque:
    //    Hereda el contexto transaccional si es llamado desde otro metodo transaccional.
    //    Las operaciones individuales de save ya son transaccionales.
    //
    //Sin embargo, si se requiere atomicidad explícita para todas las operaciones
    // del metodo o si querés asegurarte de que siempre se ejecute dentro de una
    // transacción, entonces deberías agregar la anotación @Transactional.
}
