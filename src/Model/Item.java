/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Tadeu
 */
public class Item {
    private String REF;
    private String DESCRICAO;
    private String P;
    private String M;
    private String G;
    private String U;
    private String QTDE;
    private double VLRUNIT;
    private double DESCPERC;
    private double VLRLIQUIDO;
    private double VLRTOTALITEM;

    public Item() {
    }

    public String getREF() {
        return REF;
    }

    public void setREF(String REF) {
        this.REF = REF;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getP() {
        return P;
    }

    public void setP(String P) {
        this.P = P;
    }

    public String getM() {
        return M;
    }

    public void setM(String M) {
        this.M = M;
    }

    public String getG() {
        return G;
    }

    public void setG(String G) {
        this.G = G;
    }

    public String getU() {
        return U;
    }

    public void setU(String U) {
        this.U = U;
    }

    public String getQTDE() {
        return QTDE;
    }

    public void setQTDE(String QTDE) {
        this.QTDE = QTDE;
    }

    public double getVLRUNIT() {
        return VLRUNIT;
    }

    public void setVLRUNIT(double VLRUNIT) {
        this.VLRUNIT = VLRUNIT;
    }

    public double getDESCPERC() {
        return DESCPERC;
    }

    public void setDESCPERC(double DESCPERC) {
        this.DESCPERC = DESCPERC;
    }

    public double getVLRLIQUIDO() {
        return VLRLIQUIDO;
    }

    public void setVLRLIQUIDO(double VLRLIQUIDO) {
        this.VLRLIQUIDO = VLRLIQUIDO;
    }

    public double getVLRTOTALITEM() {
        return VLRTOTALITEM;
    }

    public void setVLRTOTALITEM(double VLRTOTALITEM) {
        this.VLRTOTALITEM = VLRTOTALITEM;
    }
    
    
}
