/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "acceso_pagina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccesoPagina.findAll", query = "SELECT a FROM AccesoPagina a")
    , @NamedQuery(name = "AccesoPagina.findByIdAccesoPagina", query = "SELECT a FROM AccesoPagina a WHERE a.idAccesoPagina = :idAccesoPagina")})
public class AccesoPagina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idAcceso_Pagina")
    private Integer idAccesoPagina;
    @JoinColumn(name = "Pagina_idPagina", referencedColumnName = "idPagina")
    @ManyToOne(optional = false)
    private Pagina paginaidPagina;
    @JoinColumn(name = "Sesion_idSesion", referencedColumnName = "idSesion")
    @ManyToOne(optional = false)
    private Sesion sesionidSesion;

    public AccesoPagina() {
    }

    public AccesoPagina(Integer idAccesoPagina) {
        this.idAccesoPagina = idAccesoPagina;
    }

    public Integer getIdAccesoPagina() {
        return idAccesoPagina;
    }

    public void setIdAccesoPagina(Integer idAccesoPagina) {
        this.idAccesoPagina = idAccesoPagina;
    }

    public Pagina getPaginaidPagina() {
        return paginaidPagina;
    }

    public void setPaginaidPagina(Pagina paginaidPagina) {
        this.paginaidPagina = paginaidPagina;
    }

    public Sesion getSesionidSesion() {
        return sesionidSesion;
    }

    public void setSesionidSesion(Sesion sesionidSesion) {
        this.sesionidSesion = sesionidSesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccesoPagina != null ? idAccesoPagina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccesoPagina)) {
            return false;
        }
        AccesoPagina other = (AccesoPagina) object;
        if ((this.idAccesoPagina == null && other.idAccesoPagina != null) || (this.idAccesoPagina != null && !this.idAccesoPagina.equals(other.idAccesoPagina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AccesoPagina[ idAccesoPagina=" + idAccesoPagina + " ]";
    }
    
}
