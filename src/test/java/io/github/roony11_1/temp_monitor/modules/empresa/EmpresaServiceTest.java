package io.github.roony11_1.temp_monitor.modules.empresa;

import io.github.roony11_1.temp_monitor.modules.empresa.core.application.EmpresaService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.exceptions.EmpresaNotFoundException;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.exceptions.NombreEmpresaAlreadyExistsException;
import io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model.Empresa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EmpresaServiceTest {

    @Autowired
    private EmpresaService empresaService;

    @Test
    void crearYListar() {
        var creada = empresaService.crear("TestCorp", "Dir 123", "555-0000", "test@corp.com");
        assertNotNull(creada.getId());
        assertEquals("TestCorp", creada.getNombre());

        var list = empresaService.listarTodas();
        assertTrue(list.stream().anyMatch(e -> e.getNombre().equals("TestCorp")));
    }

    @Test
    void buscarPorId_existente() {
        var creada = empresaService.crear("BuscarCorp", "Dir", "555-0001", "buscar@corp.com");
        var encontrada = empresaService.buscarPorId(creada.getId());
        assertEquals(creada.getId(), encontrada.getId());
    }

    @Test
    void buscarPorId_inexistente_lanzaExcepcion() {
        assertThrows(EmpresaNotFoundException.class, () -> empresaService.buscarPorId(999L));
    }

    @Test
    void crearConNombreDuplicado_lanzaExcepcion() {
        empresaService.crear("DupeCorp", "Dir", "555-0002", "dupe@corp.com");
        assertThrows(NombreEmpresaAlreadyExistsException.class,
                () -> empresaService.crear("DupeCorp", "Otra Dir", "555-0003", "otro@corp.com"));
    }

    @Test
    void actualizar() {
        var creada = empresaService.crear("OldName", "Dir", "555-0004", "old@corp.com");
        var actualizada = empresaService.actualizar(creada.getId(), "NewName", "NewDir", "555-0005", "new@corp.com");
        assertEquals("NewName", actualizada.getNombre());
        assertEquals("NewDir", actualizada.getDireccion());
    }

    @Test
    void eliminar() {
        var creada = empresaService.crear("ToDelete", "Dir", "555-0006", "del@corp.com");
        empresaService.eliminar(creada.getId());
        assertThrows(EmpresaNotFoundException.class, () -> empresaService.buscarPorId(creada.getId()));
    }
}
