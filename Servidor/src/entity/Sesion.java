/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "sesion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sesion.findAll", query = "SELECT s FROM Sesion s")
    , @NamedQuery(name = "Sesion.findByIdSesion", query = "SELECT s FROM Sesion s WHERE s.idSesion = :idSesion")
    , @NamedQuery(name = "Sesion.findByEntrada", query = "SELECT s FROM Sesion s WHERE s.entrada = :entrada")
    , @NamedQuery(name = "Sesion.findBySalida", query = "SELECT s FROM Sesion s WHERE s.salida = :salida")})
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idSesion")
    private Integer idSesion;
    @Column(name = "entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrada;
    @Column(name = "salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;
    @ManyToMany(mappedBy = "sesionCollection")
    private Collection<Programa> programaCollection;
    @JoinColumn(name = "Admin_idAdmin", referencedColumnName = "idAdmin")
    @ManyToOne
    private Admin adminidAdmin;
    @JoinColumn(name = "PC_idPC", referencedColumnName = "idPC")
    @ManyToOne(optional = false)
    private Pc pCidPC;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario usuarioidUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sesionidSesion")
    private Collection<AccesoPagina> accesoPaginaCollection;

    public Sesion() {
    }

    public Sesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    @XmlTransient
    public Collection<Programa> getProgramaCollection() {
        return programaCollection;
    }

    public void setProgramaCollection(Collection<Programa> programaCollection) {
        this.programaCollection = programaCollection;
    }

    public Admin getAdminidAdmin() {
        return adminidAdmin;
    }

    public void setAdminidAdmin(Admin adminidAdmin) {
        this.adminidAdmin = adminidAdmin;
    }

    public Pc getPCidPC() {
        return pCidPC;
    }

    public void setPCidPC(Pc pCidPC) {
        this.pCidPC = pCidPC;
    }

    public Usuario getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(Usuario usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    @XmlTransient
    public Collection<AccesoPagina> getAccesoPaginaCollection() {
        return accesoPaginaCollection;
    }

    public void setAccesoPaginaCollection(Collection<AccesoPagina> accesoPaginaCollection) {
        this.accesoPaginaCollection = accesoPaginaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSesion != null ? idSesion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sesion)) {
            return false;
        }
        Sesion other = (Sesion) object;
        if ((this.idSesion == null && other.idSesion != null) || (this.idSesion != null && !this.idSesion.equals(other.idSesion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sesion[ idSesion=" + idSesion + " ]";
    }
    
}
