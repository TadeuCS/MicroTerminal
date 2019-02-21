/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.model;

import com.metre.util.MicroterminaEnum;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tadeu-PC
 */
@Entity
@Table(name = "microterminal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Microterminal.findAll", query = "SELECT m FROM Microterminal m")
    , @NamedQuery(name = "Microterminal.findByIdMicroterminal", query = "SELECT m FROM Microterminal m WHERE m.idMicroterminal = :idMicroterminal")
    , @NamedQuery(name = "Microterminal.findByIp", query = "SELECT m FROM Microterminal m WHERE m.ip = :ip")
    , @NamedQuery(name = "Microterminal.findByTipoConexao", query = "SELECT m FROM Microterminal m WHERE m.tipoConexao = :tipoConexao")
    , @NamedQuery(name = "Microterminal.findByPorta", query = "SELECT m FROM Microterminal m WHERE m.porta = :porta")
    , @NamedQuery(name = "Microterminal.findByProtocolo", query = "SELECT m FROM Microterminal m WHERE m.protocolo = :protocolo")
    , @NamedQuery(name = "Microterminal.findByInativo", query = "SELECT m FROM Microterminal m WHERE m.inativo = :inativo")})
public class Microterminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_microterminal")
    private Integer idMicroterminal;
    @Basic(optional = false)
    @Column(name = "marca", nullable = false, length = 100)
    private String marca;
    @Column(name = "marca", nullable = false)
    @Enumerated(EnumType.STRING)
    private MicroterminaEnum modelo;
    @Basic(optional = false)
    @Column(name = "ip", length = 15)
    private String ip;
    @Basic(optional = false)
    @Column(name = "tipo_conexao", nullable = false, length = 50)
    private String tipoConexao;
    @Basic(optional = false)
    @Column(name = "porta", length = 4)
    private String porta;
    @Basic(optional = false)
    @Column(name = "velocidade", length = 5)
    private String velocidade;
    @Basic(optional = false)
    @Column(name = "protocolo", length = 20)
    private String protocolo;
    @Basic(optional = false)
    @Column(name = "inativo", nullable = false, columnDefinition = "bit default 0")
    private boolean inativo;
    @Basic(optional = false)
    @Column(name = "valida_lancamentos", nullable = false, columnDefinition = "bit default 0")
    private boolean validaLancamentos;

    public Microterminal() {
    }

    public Microterminal(Integer idMicroterminal) {
        this.idMicroterminal = idMicroterminal;
    }

    public Microterminal(String ip, String tipoConexao, String porta, String protocolo, boolean inativo, boolean validaLancamentos, MicroterminaEnum modelo) {
        this.idMicroterminal = idMicroterminal;
        this.ip = ip;
        this.tipoConexao = tipoConexao;
        this.porta = porta;
        this.protocolo = protocolo;
        this.inativo = inativo;
        this.validaLancamentos = validaLancamentos;
        this.modelo = modelo;
    }

    public Integer getIdMicroterminal() {
        return idMicroterminal;
    }

    public void setIdMicroterminal(Integer idMicroterminal) {
        this.idMicroterminal = idMicroterminal;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTipoConexao() {
        return tipoConexao;
    }

    public void setTipoConexao(String tipoConexao) {
        this.tipoConexao = tipoConexao;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public boolean getInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public boolean getValidaLancamentos() {
        return validaLancamentos;
    }

    public void setValidaLancamentos(boolean validaLancamentos) {
        this.validaLancamentos = validaLancamentos;
    }

    public MicroterminaEnum getModelo() {
        return modelo;
    }

    public void setModelo(MicroterminaEnum modelo) {
        this.modelo = modelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMicroterminal != null ? idMicroterminal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Microterminal)) {
            return false;
        }
        Microterminal other = (Microterminal) object;
        if ((this.idMicroterminal == null && other.idMicroterminal != null) || (this.idMicroterminal != null && !this.idMicroterminal.equals(other.idMicroterminal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.olivet.model.Microterminal[ idMicroterminal=" + idMicroterminal + " ]";
    }

}
