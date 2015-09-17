/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Cliente;
import Model.Item;
import Model.Pedido;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Teste {

    Statement st;
    ResultSet rs;
    Conexao conexao;
    Item item;
    Pedido pedido;
    Cliente cliente;

    public Pedido listPedidoByCodigo(String codigo) {
        pedido = new Pedido();
        cliente = new Cliente();
        conexao = new Conexao();
        try {
            st = conexao.getConexao();
            rs = st.executeQuery("select\n"
                    + "p.codpedido,v.nome vendedor,p.datapedido,c.nome,c.nomefantasia,c.enderecoent,c.bairroent,\n"
                    + "c.cepent,cid.cidade,cid.estado,c.email,c.fone,c.celular,c.cgccpf,c.inscest,g.descricao pagamento,\n"
                    + "c.contato,p.dataentrega,p.vlracrescimo,p.totalpedido,p.observacao1,p.observacao2,p.observacao3\n"
                    + "from pedidoc p\n"
                    + "inner join cliente c on p.codcliente=c.codcliente\n"
                    + "inner join vendend v on p.codvendedor=v.codvendedor\n"
                    + "inner join cidades cid on c.codcidade=cid.codcidade\n"
                    + "inner join condpag g on p.codprazo=g.codprazo\n"
                    + "where p.codpedido='"+trataCodigo(codigo)+"' and p.tipopedido='55'");
            while (rs.next()) {
                pedido.setCODPEDIDO(rs.getString("codpedido"));
                pedido.setVENDEDOR(rs.getString("vendedor"));
                pedido.setDATAPEDIDO(Data.getDataByDate(Data.getDataByTexto(rs.getString("datapedido").replace("-", "/"), "yyyy/MM/dd"), "dd/MM/yyyy"));
                cliente.setNOME(rs.getString("nome"));
                cliente.setNOMEFANTASIA(rs.getString("nomefantasia"));
                cliente.setENDERECOENT(rs.getString("enderecoent"));
                cliente.setBAIRROENT(rs.getString("bairroent"));
                cliente.setCEPENT(rs.getString("cepent"));
                cliente.setCIDADE(rs.getString("cidade"));
                cliente.setESTADO(rs.getString("estado"));
                cliente.setEMAIL(rs.getString("email"));
                cliente.setFONE(rs.getString("fone"));
                cliente.setCELULAR(rs.getString("celular"));
                cliente.setCGCCPF(rs.getString("cgccpf"));
                cliente.setINSCEST(rs.getString("inscest"));
                pedido.setPAGAMENTO(rs.getString("pagamento"));
                cliente.setCONTATO(rs.getString("contato"));
                pedido.setDATAENTREGA(Data.getDataByDate(Data.getDataByTexto(rs.getString("datapedido").replace("-", "/"), "yyyy/MM/dd"), "dd/MM/yyyy"));
                pedido.setVLRACRESCIMO(rs.getString("vlracrescimo"));
                pedido.setTOTALPEDIDO(rs.getString("totalpedido"));
                pedido.setOBSERVACAO1(rs.getString("observacao1"));
                pedido.setOBSERVACAO2(rs.getString("observacao2"));
                pedido.setOBSERVACAO3(rs.getString("observacao3"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar o Pedido "+trataCodigo(codigo)+"!\n" + e);
        }
        return pedido;
    }
    public Pedido listItensByCodigo(String codigo) {
        item = new Item();
        conexao = new Conexao();
        try {
            st = conexao.getConexao();
            rs = st.executeQuery("");
            while (rs.next()) {
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar o Itens!\n" + e);
        }
        return pedido;
    }

    public String trataCodigo(String codigo) {
        while (codigo.length() < 8) {
            codigo = "0" + codigo;
        }
        return codigo;
    }

    public static void main(String[] args) {
        Teste teste = new Teste();
        teste.listPedidoByCodigo("1");
        //Fazer aqui um método que busca os dados no banco de dados e gera o relatório
    }

}
