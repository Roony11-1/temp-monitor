package io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model;

import java.time.Instant;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "sucursales")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Sucursal extends PanacheEntity
{
    @Column(nullable = false)
    private String nombre;

    private String direccion;

    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    private Empresa empresa;

    @Builder.Default
    private boolean activo = true;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    public Long getId()
    {
        return this.id;
    }
}
