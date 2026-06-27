package io.github.roony11_1.temp_monitor.modules.empresa;

import io.github.roony11_1.temp_monitor.modules.empresa.core.application.EmpresaService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.SucursalService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.exceptions.SucursalNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private EmpresaService empresaService;

    private Long empresaId;

    @BeforeEach
    void setUp() {
        empresaId = empresaService.crear("Empresa Sucursales", "Dir", "555-0100", "emp@test.com").getId();
    }

    @Test
    void crearYListar() {
        var creada = sucursalService.crear("Suc Central", "Av Central 123", "555-0101", empresaId);
        assertNotNull(creada.getId());
        assertEquals("Suc Central", creada.getNombre());

        var list = sucursalService.listarTodas();
        assertTrue(list.stream().anyMatch(s -> s.getNombre().equals("Suc Central")));
    }

    @Test
    void listarPorEmpresa() {
        sucursalService.crear("Suc A", "Dir A", "555-0102", empresaId);
        var list = sucursalService.listarPorEmpresa(empresaId);
        assertTrue(list.stream().allMatch(s -> s.getEmpresa().getId().equals(empresaId)));
    }

    @Test
    void buscarPorId_inexistente_lanzaExcepcion() {
        assertThrows(SucursalNotFoundException.class, () -> sucursalService.buscarPorId(999L));
    }

    @Test
    void actualizar() {
        var creada = sucursalService.crear("Old", "Dir", "555-0103", empresaId);
        var actualizada = sucursalService.actualizar(creada.getId(), "New", "NewDir", "555-0104", empresaId);
        assertEquals("New", actualizada.getNombre());
    }

    @Test
    void activarDesactivar() {
        var creada = sucursalService.crear("Toggle", "Dir", "555-0105", empresaId);
        sucursalService.desactivar(creada.getId());
        assertFalse(sucursalService.buscarPorId(creada.getId()).isActivo());
        sucursalService.activar(creada.getId());
        assertTrue(sucursalService.buscarPorId(creada.getId()).isActivo());
    }
}
