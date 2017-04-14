/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "pc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pc.findAll", query = "SELECT p FROM Pc p")
    , @NamedQuery(name = "Pc.findByIdPC", query = "SELECT p FROM Pc p WHERE p.idPC = :idPC")
    , @NamedQuery(name = "Pc.findByModelo", query = "SELECT p FROM Pc p WHERE p.modelo = :modelo")
    , @NamedQuery(name = "Pc.findByProcesador", query = "SELECT p FROM Pc p WHERE p.procesador = :procesador")
    , @NamedQuery(name = "Pc.findByRam", query = "SELECT p FROM Pc p WHERE p.ram = :ram")
    , @NamedQuery(name = "Pc.findByDiscoDuro", query = "SELECT p FROM Pc p WHERE p.discoDuro = :discoDuro")
    , @NamedQuery(name = "Pc.findByNoSerie", query = "SELECT p FROM Pc p WHERE p.noSerie = :noSerie")
    , @NamedQuery(name = "Pc.findByOs", query = "SELECT p FROM Pc p WHERE p.os = :os")})
public class Pc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPC")
    private Integer idPC;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "procesador")
    private String procesador;
    @Column(name = "ram")
    private String ram;
    @Column(name = "disco_duro")
    private String discoDuro;
    @Column(name = "no_serie")
    private String noSerie;
    @Column(name = "os")
    private String os;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pCidPC")
    private Collection<Sesion> sesionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pCidPC")
    private Collection<UsoPc> usoPcCollection;

    public Pc() {
    }

    public Pc(Integer idPC) {
        this.idPC = idPC;
    }

    public Integer getIdPC() {
        return idPC;
    }

    public void setIdPC(Integer idPC) {
        this.idPC = idPC;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @XmlTransient
    public Collection<Sesion> getSesionCollection() {
        return sesionCollection;
    }

    public void setSesionCollection(Collection<Sesion> sesionCollection) {
        this.sesionCollection = sesionCollection;
    }

    @XmlTransient
    public Collection<UsoPc> getUsoPcCollection() {
        return usoPcCollection;
    }

    public void setUsoPcCollection(Collection<UsoPc> usoPcCollection) {
        this.usoPcCollection = usoPcCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPC != null ? idPC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pc)) {
            return false;
        }
        Pc other = (Pc) object;
        if ((this.idPC == null && other.idPC != null) || (this.idPC != null && !this.idPC.equals(other.idPC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pc[ idPC=" + idPC + " ]";
    }
    
}
