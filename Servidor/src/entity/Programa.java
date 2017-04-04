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
@Table(name = "programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p")
    , @NamedQuery(name = "Programa.findByIdPROGRAMA", query = "SELECT p FROM Programa p WHERE p.idPROGRAMA = :idPROGRAMA")
    , @NamedQuery(name = "Programa.findByNombre", query = "SELECT p FROM Programa p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Programa.findByProceso", query = "SELECT p FROM Programa p WHERE p.proceso = :proceso")
    , @NamedQuery(name = "Programa.findByDescripci\u00f3n", query = "SELECT p FROM Programa p WHERE p.descripci\u00f3n = :descripci\u00f3n")})
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPROGRAMA")
    private Integer idPROGRAMA;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "proceso")
    private String proceso;
    @Column(name = "Descripci\u00f3n")
    private String descripción;
    @JoinTable(name = "uso_programa", joinColumns = {
        @JoinColumn(name = "PROGRAMA_idPROGRAMA", referencedColumnName = "idPROGRAMA")}, inverseJoinColumns = {
        @JoinColumn(name = "SESION_idSesion", referencedColumnName = "idSesion")})
    @ManyToMany
    private Collection<Sesion> sesionCollection;

    public Programa() {
    }

    public Programa(Integer idPROGRAMA) {
        this.idPROGRAMA = idPROGRAMA;
    }

    public Programa(Integer idPROGRAMA, String nombre, String proceso) {
        this.idPROGRAMA = idPROGRAMA;
        this.nombre = nombre;
        this.proceso = proceso;
    }

    public Integer getIdPROGRAMA() {
        return idPROGRAMA;
    }

    public void setIdPROGRAMA(Integer idPROGRAMA) {
        this.idPROGRAMA = idPROGRAMA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
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
        hash += (idPROGRAMA != null ? idPROGRAMA.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programa)) {
            return false;
        }
        Programa other = (Programa) object;
        if ((this.idPROGRAMA == null && other.idPROGRAMA != null) || (this.idPROGRAMA != null && !this.idPROGRAMA.equals(other.idPROGRAMA))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Programa[ idPROGRAMA=" + idPROGRAMA + " ]";
    }
    
}
