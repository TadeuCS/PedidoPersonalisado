/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Cliente;
import Model.Item;
import Model.Pedido;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu
 */
public class Gerador {

    Statement st;
    Statement st2;
    Statement st3;
    ResultSet rs;
    ResultSet rs2;
    ResultSet rs3;
    Conexao conexao;
    Item item;
    Pedido pedido;
    Cliente cliente;

    public static void main(String[] args) {
        Gerador gerador=new Gerador();
        gerador.geraRelatorio(JOptionPane.showInputDialog("Informe o número do PDV:"));
    }
    
    public void geraRelatorio(String codpedido){
        try {
            Map parameters = new HashMap();
        GeraRelatorios geraRelatorios = new GeraRelatorios();
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(listPedidoByCodigo(trataCodigo(codpedido)));
        if (geraRelatorios.imprimirByLista("Rel_Pedido_Itens.jasper", parameters, pedidos) == false) {
                                geraRelatorios.imprimirByLista("src/Relatorios/Rel_Pedido_Itens.jasper", parameters, pedidos);
                            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    private Pedido listPedidoByCodigo(String codigo) {
        pedido = new Pedido();
        cliente = new Cliente();
        conexao = new Conexao();
        try {
            st = conexao.getConexao();
            rs = st.executeQuery("select\n"
                    + "p.codpedido,v.nome vendedor,p.datapedido,c.nome razao,c.nomefantasia,c.enderecoent,c.bairroent,\n"
                    + "c.cepent,cid.cidade,cid.estado,c.email,c.fone,c.celular,c.cgccpf,c.inscest,g.descricao pagamento,\n"
                    + "c.contato,p.dataentrega,p.vlracrescimo,p.totalpedido,p.observacao1,p.observacao2,p.observacao3\n"
                    + "from pedidoc p\n"
                    + "inner join cliente c on p.codcliente=c.codcliente\n"
                    + "inner join vendend v on p.codvendedor=v.codvendedor\n"
                    + "inner join cidades cid on c.codcidade=cid.codcidade\n"
                    + "inner join condpag g on p.codprazo=g.codprazo\n"
                    + "where p.codpedido='" + codigo + "' and p.tipopedido='55'");
            while (rs.next()) {
                pedido.setCODPEDIDO(rs.getString("codpedido"));
                pedido.setVENDEDOR(rs.getString("vendedor"));
                pedido.setDATAPEDIDO(Data.getDataByDate(Data.getDataByTexto(rs.getString("datapedido").replace("-", "/"), "yyyy/MM/dd"), "dd/MM/yyyy"));
                cliente.setNOME(rs.getString("razao"));
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
                pedido.setItens(listItensByPedido(codigo));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar o Pedido " + codigo + "!\n" + e);
        }
        return pedido;
    }

    private List<Item> listItensByPedido(String codpedido) {
        conexao = new Conexao();
        item = new Item();
        List<Item> itens = new ArrayList<>();
        try {
            st2 = conexao.getConexao();
            rs2 = st2.executeQuery("select\n"
                    + "i.codprod,p.referencia,p.descricao,i.precounit,c.descontoperc\n"
                    + "from pedidoi i\n"
                    + "inner join produto p on i.codprod=p.codprod\n"
                    + "inner join pedidoc c on i.codpedido=c.codpedido\n"
                    + "where i.codpedido= '" + codpedido + "' and i.tipopedido='55'\n"
                    + "group by i.codprod ,p.referencia,p.descricao,i.precounit,c.descontoperc");
            while (rs2.next()) {
                Double vlrUnitario = Double.parseDouble(rs2.getString("precounit"));
                Double percentual = Double.parseDouble(rs2.getString("descontoperc"));
                Double vlrLiquido = vlrUnitario - (vlrUnitario * percentual / 100);
                Integer qtdeP = Integer.parseInt(retornaQtdeByTamanho(codpedido, rs2.getString("codprod"), "= 'P'"));
                Integer qtdeM = Integer.parseInt(retornaQtdeByTamanho(codpedido, rs2.getString("codprod"), "= 'M'"));
                Integer qtdeG = Integer.parseInt(retornaQtdeByTamanho(codpedido, rs2.getString("codprod"), "= 'G'"));
                Integer qtdeU = Integer.parseInt(retornaQtdeByTamanho(codpedido, rs2.getString("codprod"), "IS NULL"));
                Integer qtdeTotal = qtdeP + qtdeM + qtdeG + qtdeU;

                Double totalItem = qtdeTotal * vlrUnitario;

                item.setREF("referencia");
                item.setDESCRICAO("descricao");
                item.setP(qtdeP + "");
                item.setM(qtdeM + "");
                item.setM(qtdeG + "");
                item.setU(qtdeU + "");
                item.setQTDE(qtdeTotal + "");
                item.setVLRUNIT(vlrUnitario);
                item.setDESCPERC(percentual);
                item.setVLRLIQUIDO(vlrLiquido);
                item.setVLRTOTALITEM(totalItem);
                itens.add(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Listar o Itens!\n" + e);
        }
        return itens;
    }

    private String trataCodigo(String codigo) {
        while (codigo.length() < 8) {
            codigo = "0" + codigo;
        }
        return codigo;
    }

    private String retornaQtdeByTamanho(String codpedido, String codprod, String grade) {
        conexao = new Conexao();
        String qtde = "0";
        try {
            st3 = conexao.getConexao();
            rs3 = st3.executeQuery("Select\n"
                    + "quantidade\n"
                    + "From pedidoi i\n"
                    + "left join grade g on i.codgrade=g.codgrade\n"
                    + "Where i.codpedido = '" + codpedido + "' and\n"
                    + "i.codprod = '" + codprod + "'\n"
                    + "and g.descricao " + grade + ";");
            while (rs3.next()) {
                int i = (int) Double.parseDouble(rs3.getString("quantidade"));
                qtde = i + "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a quantidade de produtos por grade!\n" + e);
        }
        return qtde;
    }

}
