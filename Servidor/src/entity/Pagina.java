/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "pagina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagina.findAll", query = "SELECT p FROM Pagina p")
    , @NamedQuery(name = "Pagina.findByIdPagina", query = "SELECT p FROM Pagina p WHERE p.idPagina = :idPagina")
    , @NamedQuery(name = "Pagina.findByNombrePagina", query = "SELECT p FROM Pagina p WHERE p.nombrePagina = :nombrePagina")})
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPagina")
    private Integer idPagina;
    @Column(name = "nombrePagina")
    private String nombrePagina;
    @ManyToMany(mappedBy = "paginaCollection")
    private Collection<Sesion> sesionCollection;

    public Pagina() {
    }

    public Pagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public String getNombrePagina() {
        return nombrePagina;
    }

    public void setNombrePagina(String nombrePagina) {
        this.nombrePagina = nombrePagina;
    }

    @XmlTransient
    public Collection<Sesion> getSesionCollection() {
        return sesionCollection;
    }

    public void setSesionCollection(Collection<Sesion> sesionCollection) {
        this.sesionCollection = sesionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagina != null ? idPagina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagina)) {
            return false;
        }
        Pagina other = (Pagina) object;
        if ((this.idPagina == null && other.idPagina != null) || (this.idPagina != null && !this.idPagina.equals(other.idPagina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pagina[ idPagina=" + idPagina + " ]";
    }
    
}
