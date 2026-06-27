package io.github.roony11_1.temp_monitor.modules.camara;

import io.github.roony11_1.temp_monitor.modules.camara.core.application.CamaraService;
import io.github.roony11_1.temp_monitor.modules.camara.core.domain.exceptions.CamaraNotFoundException;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.EmpresaService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.SucursalService;
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
class CamaraServiceTest {

    @Autowired
    private CamaraService camaraService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private SucursalService sucursalService;

    private Long sucursalId;

    @BeforeEach
    void setUp() {
        var empresaId = empresaService.crear("Empresa Camaras", "Dir", "555-0200", "cam@test.com").getId();
        sucursalId = sucursalService.crear("Suc Camaras", "Dir Suc", "555-0201", empresaId).getId();
    }

    @Test
    void crearYListar() {
        var creada = camaraService.crear("Cámara 1", "Descripción", sucursalId, -20.0, -10.0);
        assertNotNull(creada.getId());
        assertEquals("Cámara 1", creada.getNombre());
        assertEquals(-20.0, creada.getTemperaturaMinima());
        assertEquals(-10.0, creada.getTemperaturaMaxima());

        var list = camaraService.listarTodas();
        assertTrue(list.stream().anyMatch(c -> c.getNombre().equals("Cámara 1")));
    }

    @Test
    void listarPorSucursal() {
        camaraService.crear("Cam A", "Desc", sucursalId, 0.0, 5.0);
        var list = camaraService.listarPorSucursal(sucursalId);
        assertTrue(list.stream().allMatch(c -> c.getSucursal().getId().equals(sucursalId)));
    }

    @Test
    void buscarPorId_inexistente_lanzaExcepcion() {
        assertThrows(CamaraNotFoundException.class, () -> camaraService.buscarPorId(999L));
    }

    @Test
    void actualizar() {
        var creada = camaraService.crear("Old", "Desc", sucursalId, -10.0, 0.0);
        var actualizada = camaraService.actualizar(creada.getId(), "New", "NewDesc", sucursalId, -15.0, -5.0);
        assertEquals("New", actualizada.getNombre());
        assertEquals(-15.0, actualizada.getTemperaturaMinima());
    }
}
