/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import cliente.Tarea;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.Entity;
import org.hibernate.HibernateException;
import servidor.bdUtil;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSesion")
    private Long idSesion;
    @Column(name = "entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrada;
    @Column(name = "salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;
    @JoinTable(name = "uso_programa", joinColumns = {
        @JoinColumn(name = "SESION_idSesion", referencedColumnName = "idSesion")}, inverseJoinColumns = {
        @JoinColumn(name = "PROGRAMA_idPROGRAMA", referencedColumnName = "idPROGRAMA")})
    @ManyToMany
    private Collection<Programa> programaCollection;
    @JoinTable(name = "acceso_pagina", joinColumns = {
        @JoinColumn(name = "Sesion_idSesion", referencedColumnName = "idSesion")}, inverseJoinColumns = {
        @JoinColumn(name = "Pagina_idPagina", referencedColumnName = "idPagina")})
    @ManyToMany
    private Collection<Pagina> paginaCollection;
    @JoinColumn(name = "Admin_idAdmin", referencedColumnName = "idAdmin")
    @ManyToOne
    private Admin adminidAdmin;
    @JoinColumn(name = "PC_idPC", referencedColumnName = "idPC")
    @ManyToOne(optional = false)
    private Pc pCidPC;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "codigo")
    @ManyToOne
    private Usuario usuarioidUsuario;

    public Sesion() {
    }

    public Sesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    public Long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Long idSesion) {
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

    @XmlTransient
    public Collection<Pagina> getPaginaCollection() {
        return paginaCollection;
    }
    
    public void setPaginaCollection(Collection<Pagina> paginaCollection) {
        this.paginaCollection = paginaCollection;
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

    /***************************************************/
    /********************************************************/
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
