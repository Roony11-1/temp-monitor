package io.github.roony11_1.temp_monitor.modules.empresa.core.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "empresas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Empresa extends PanacheEntity
{
    @Column(nullable = false)
    private String nombre;

    private String direccion;

    private String telefono;

    private String email;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Sucursal> sucursales = new ArrayList<>();

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
