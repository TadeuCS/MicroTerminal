/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.util;

import java.math.BigDecimal;

/**
 *
 * @author Tadeu-pc
 */

public class PedidoItemPojo {
    private Integer codProduto;
    private BigDecimal quantidade;

    public PedidoItemPojo(Integer codProduto, BigDecimal quantidade) {
        this.codProduto = codProduto;
        this.quantidade = quantidade;
    }

    public PedidoItemPojo() {
    }

    public Integer getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(Integer codProduto) {
        this.codProduto = codProduto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "PedidoItemPojo{" + "codProduto=" + codProduto + ", quantidade=" + quantidade + '}';
    }
}
