/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.util;

/**
 *
 * @author Tadeu-pc
 */
public enum MicroterminaEnum {
    WILBOR_16("Wilbor Tech 2x16"),//2x16
    WILBOR_44("Wilbor Tech 2x44"),//2x40
    MT_720("Gertec MT 720 2x20"),//2x20
    MT_740("Gertec MT 740 2x40");//2x40

    private String descricao;

    MicroterminaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
