/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class Pedido {
    private String CODPEDIDO;
    private String VENDEDOR;
    private String DATAPEDIDO;
    private String PAGAMENTO;
    private String DATAENTREGA;
    private String VLRACRESCIMO;
    private String TOTALPEDIDO;
    private String OBSERVACAO1;
    private String OBSERVACAO2;
    private String OBSERVACAO3;
    private Cliente cliente;
    private List<Item> itens=new ArrayList<>();

    public Pedido() {
    }

    public String getCODPEDIDO() {
        return CODPEDIDO;
    }

    public void setCODPEDIDO(String CODPEDIDO) {
        this.CODPEDIDO = CODPEDIDO;
    }

    public String getVENDEDOR() {
        return VENDEDOR;
    }

    public void setVENDEDOR(String VENDEDOR) {
        this.VENDEDOR = VENDEDOR;
    }

    public String getDATAPEDIDO() {
        return DATAPEDIDO;
    }

    public void setDATAPEDIDO(String DATAPEDIDO) {
        this.DATAPEDIDO = DATAPEDIDO;
    }

    public String getPAGAMENTO() {
        return PAGAMENTO;
    }

    public void setPAGAMENTO(String PAGAMENTO) {
        this.PAGAMENTO = PAGAMENTO;
    }

    public String getDATAENTREGA() {
        return DATAENTREGA;
    }

    public void setDATAENTREGA(String DATAENTREGA) {
        this.DATAENTREGA = DATAENTREGA;
    }

    public String getVLRACRESCIMO() {
        return VLRACRESCIMO;
    }

    public void setVLRACRESCIMO(String VLRACRESCIMO) {
        this.VLRACRESCIMO = VLRACRESCIMO;
    }

    public String getTOTALPEDIDO() {
        return TOTALPEDIDO;
    }

    public void setTOTALPEDIDO(String TOTALPEDIDO) {
        this.TOTALPEDIDO = TOTALPEDIDO;
    }

    public String getOBSERVACAO1() {
        return OBSERVACAO1;
    }

    public void setOBSERVACAO1(String OBSERVACAO1) {
        this.OBSERVACAO1 = OBSERVACAO1;
    }

    public String getOBSERVACAO2() {
        return OBSERVACAO2;
    }

    public void setOBSERVACAO2(String OBSERVACAO2) {
        this.OBSERVACAO2 = OBSERVACAO2;
    }

    public String getOBSERVACAO3() {
        return OBSERVACAO3;
    }

    public void setOBSERVACAO3(String OBSERVACAO3) {
        this.OBSERVACAO3 = OBSERVACAO3;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
    
}
