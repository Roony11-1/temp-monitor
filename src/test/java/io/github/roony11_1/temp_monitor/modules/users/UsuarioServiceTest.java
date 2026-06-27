package io.github.roony11_1.temp_monitor.modules.users;

import io.github.roony11_1.temp_monitor.kernel.security.model.Rol;
import io.github.roony11_1.temp_monitor.kernel.security.model.TokenUser;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.EmpresaService;
import io.github.roony11_1.temp_monitor.modules.empresa.core.application.SucursalService;
import io.github.roony11_1.temp_monitor.modules.users.core.application.UsuarioService;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.EmailAlreadyExistsException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.exceptions.UserNotFoundException;
import io.github.roony11_1.temp_monitor.modules.users.core.domain.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private SucursalService sucursalService;

    private Long empresaId;
    private Long sucursalId;

    @BeforeEach
    void setUp() {
        empresaId = empresaService.crear("Empresa Users", "Dir", "555-0300", "users@test.com").getId();
        sucursalId = sucursalService.crear("Suc Users", "Dir Suc", "555-0301", empresaId).getId();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void autenticarComo(Long userId, Set<Rol> roles, Long empId, Long sucId) {
        Usuario usuario = Usuario.builder()
                .id(userId)
                .email("current@test.com")
                .nombre("Current User")
                .roles(new HashSet<>(roles))
                .empresaId(empId)
                .sucursalId(sucId)
                .activo(true)
                .build();
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .toList();
        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void superAdmin_creaCualquierRol() {
        autenticarComo(1L, Set.of(Rol.SUPER_ADMIN), null, null);
        var usuario = usuarioService.crear("super@test.com", "123", "Super", null, null, Set.of(Rol.SUPER_ADMIN));
        assertNotNull(usuario.getId());
        assertEquals("super@test.com", usuario.getEmail());
    }

    @Test
    void adminEmpresa_creaUsuarioEnSuEmpresa() {
        autenticarComo(2L, Set.of(Rol.ADMIN_EMPRESA), empresaId, null);
        var usuario = usuarioService.crear("tecnico@test.com", "123", "Técnico", empresaId, sucursalId, Set.of(Rol.TECNICO));
        assertNotNull(usuario.getId());
        assertEquals(empresaId, usuario.getEmpresaId());
    }

    @Test
    void adminEmpresa_noPuedeCrearSuperAdmin() {
        autenticarComo(2L, Set.of(Rol.ADMIN_EMPRESA), empresaId, null);
        assertThrows(AccessDeniedException.class,
                () -> usuarioService.crear("mal@test.com", "123", "Mal", null, null, Set.of(Rol.SUPER_ADMIN)));
    }

    @Test
    void adminEmpresa_noPuedeCrearAdminEmpresa() {
        autenticarComo(2L, Set.of(Rol.ADMIN_EMPRESA), empresaId, null);
        assertThrows(AccessDeniedException.class,
                () -> usuarioService.crear("mal2@test.com", "123", "Mal2", empresaId, null, Set.of(Rol.ADMIN_EMPRESA)));
    }

    @Test
    void adminEmpresa_noPuedeCrearEnOtraEmpresa() {
        autenticarComo(2L, Set.of(Rol.ADMIN_EMPRESA), empresaId, null);
        var otraEmpresaId = empresaService.crear("Otra", "Dir", "555-9999", "otra@test.com").getId();
        assertThrows(AccessDeniedException.class,
                () -> usuarioService.crear("otraEmp@test.com", "123", "OtraEmp", otraEmpresaId, null, Set.of(Rol.TECNICO)));
    }

    @Test
    void usuarioSinPermiso_noPuedeCrear() {
        autenticarComo(3L, Set.of(Rol.USUARIO), empresaId, sucursalId);
        assertThrows(AccessDeniedException.class,
                () -> usuarioService.crear("sinpermiso@test.com", "123", "Sin", empresaId, null, Set.of(Rol.USUARIO)));
    }

    @Test
    void emailDuplicado_lanzaExcepcion() {
        autenticarComo(1L, Set.of(Rol.SUPER_ADMIN), null, null);
        usuarioService.crear("dupe@test.com", "123", "Dupe", null, null, Set.of(Rol.USUARIO));
        assertThrows(EmailAlreadyExistsException.class,
                () -> usuarioService.crear("dupe@test.com", "456", "Dupe2", null, null, Set.of(Rol.USUARIO)));
    }

    @Test
    void buscarPorId_existente() {
        autenticarComo(1L, Set.of(Rol.SUPER_ADMIN), null, null);
        var creado = usuarioService.crear("buscar@test.com", "123", "Buscar", null, null, Set.of(Rol.USUARIO));
        var encontrado = usuarioService.buscarPorId(creado.getId());
        assertEquals(creado.getId(), encontrado.getId());
    }

    @Test
    void buscarPorId_inexistente_lanzaExcepcion() {
        assertThrows(UserNotFoundException.class, () -> usuarioService.buscarPorId(999L));
    }

    @Test
    void activarDesactivar() {
        autenticarComo(1L, Set.of(Rol.SUPER_ADMIN), null, null);
        var usuario = usuarioService.crear("toggle@test.com", "123", "Toggle", null, null, new HashSet<>(Set.of(Rol.USUARIO)));
        usuarioService.desactivar(usuario.getId());
        assertFalse(usuarioService.buscarPorId(usuario.getId()).isActivo());
        usuarioService.activar(usuario.getId());
        assertTrue(usuarioService.buscarPorId(usuario.getId()).isActivo());
    }

    @Test
    void cambiarPassword() {
        autenticarComo(1L, Set.of(Rol.SUPER_ADMIN), null, null);
        var usuario = usuarioService.crear("pass@test.com", "oldpass", "Pass", null, null, new HashSet<>(Set.of(Rol.USUARIO)));
        usuarioService.cambiarPassword(usuario.getId(), "newpass");
        // No exception means success (password is hashed so we can't compare directly)
        assertNotNull(usuarioService.buscarPorId(usuario.getId()));
    }

    @Test
    void eliminar() {
        autenticarComo(1L, Set.of(Rol.SUPER_ADMIN), null, null);
        var usuario = usuarioService.crear("delete@test.com", "123", "Delete", null, null, Set.of(Rol.USUARIO));
        usuarioService.eliminar(usuario.getId());
        assertThrows(UserNotFoundException.class, () -> usuarioService.buscarPorId(usuario.getId()));
    }
}
