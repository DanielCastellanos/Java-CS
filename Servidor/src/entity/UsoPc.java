/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "uso_pc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoPc.findAll", query = "SELECT u FROM UsoPc u")
    , @NamedQuery(name = "UsoPc.findByIdUSOPC", query = "SELECT u FROM UsoPc u WHERE u.idUSOPC = :idUSOPC")
    , @NamedQuery(name = "UsoPc.findByEncendido", query = "SELECT u FROM UsoPc u WHERE u.encendido = :encendido")
    , @NamedQuery(name = "UsoPc.findByApagado", query = "SELECT u FROM UsoPc u WHERE u.apagado = :apagado")})
public class UsoPc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUSO_PC")
    private Integer idUSOPC;
    @Basic(optional = false)
    @Column(name = "Encendido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date encendido;
    @Column(name = "apagado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date apagado;
    @JoinColumn(name = "PC_idPC", referencedColumnName = "idPC")
    @ManyToOne(optional = false)
    private Pc pCidPC;

    public UsoPc() {
    }

    public UsoPc(Integer idUSOPC) {
        this.idUSOPC = idUSOPC;
    }

    public UsoPc(Integer idUSOPC, Date encendido) {
        this.idUSOPC = idUSOPC;
        this.encendido = encendido;
    }

    public Integer getIdUSOPC() {
        return idUSOPC;
    }

    public void setIdUSOPC(Integer idUSOPC) {
        this.idUSOPC = idUSOPC;
    }

    public Date getEncendido() {
        return encendido;
    }

    public void setEncendido(Date encendido) {
        this.encendido = encendido;
    }

    public Date getApagado() {
        return apagado;
    }

    public void setApagado(Date apagado) {
        this.apagado = apagado;
    }

    public Pc getPCidPC() {
        return pCidPC;
    }

    public void setPCidPC(Pc pCidPC) {
        this.pCidPC = pCidPC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUSOPC != null ? idUSOPC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsoPc)) {
            return false;
        }
        UsoPc other = (UsoPc) object;
        if ((this.idUSOPC == null && other.idUSOPC != null) || (this.idUSOPC != null && !this.idUSOPC.equals(other.idUSOPC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UsoPc[ idUSOPC=" + idUSOPC + " ]";
    }
    
}
