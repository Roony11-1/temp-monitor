package io.github.roony11_1.temp_monitor.modules.users.core.domain.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.github.roony11_1.temp_monitor.kernel.security.Rol;
import io.github.roony11_1.temp_monitor.kernel.security.TokenUser;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Usuario extends PanacheEntity implements TokenUser
{
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();

    private String nombre;
    private String telefono;

    private Long empresaId;

    @Builder.Default
    private Instant createdAt = Instant.now();
    private Instant updatedAt;
    private Instant lastLogin;

    @Builder.Default
    private boolean activo = true;

    public Long getId()
    {
        return this.id;
    }
}
