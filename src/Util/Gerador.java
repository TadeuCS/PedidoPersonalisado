/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.Item;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
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
    Map parameters;

    public Gerador() {
        conexao = new Conexao();
    }

    public static void main(String[] args) {
        Gerador gerador = new Gerador();
        gerador.geraRelatorio(JOptionPane.showInputDialog("Informe o número do PDV:"));
    }

    public void geraRelatorio(String codpedido) {
        try {
            GeraRelatorios geraRelatorios = new GeraRelatorios();
            if (geraRelatorios.imprimirByLista("TesteItens.jasper", getParametros(trataCodigo(codpedido)), testaListaItens()) == false) {
                geraRelatorios.imprimirByLista("src/Relatorios/TesteItens.jasper", getParametros(trataCodigo(codpedido)), testaListaItens());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private List<Item> listItensByPedido(String codpedido) {
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
                if (rs3.getString("quantidade").equals("null") == true) {
                    qtde = "0";
                } else {
                    int i = (int) Double.parseDouble(rs3.getString("quantidade"));
                    qtde = i + "";
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a quantidade de produtos por grade!\n" + e);
        }
        return qtde;
    }

    private void imprimeItens(String codpedido) {
        List<Item> itens = listItensByPedido(codpedido);
        for (Item item : itens) {
            System.out.println("Referencia: " + item.getREF());
            System.out.println("Descricao: " + item.getDESCRICAO());
            System.out.println("Qtde P: " + item.getQtdeP());
            System.out.println("Qtde M: " + item.getQtdeM());
            System.out.println("Qtde G: " + item.getQtdeG());
            System.out.println("Qtde U: " + item.getQtdeU());
            System.out.println("Qtde QTDE: " + item.getQTDE());
            System.out.println("VLRUNITARIO: " + item.getVLRUNIT());
            System.out.println("DESCONTO %: " + item.getDESCPERC());
            System.out.println("VLRLIQUIDO: " + item.getVLRLIQUIDO());
            System.out.println("TOTAL ITEM: " + item.getVLRTOTALITEM());
        }
    }

    private List<Item> testaListaItens() {
        List<Item> itens = new ArrayList<>();
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setREF("123");
        item1.setDESCRICAO("teste1");
        item1.setQtdeP("10");
        item1.setQtdeM("20");
        item1.setQtdeG("30");
        item1.setQTDE("60");
        item1.setVLRUNIT(10.0);
        item1.setDESCPERC(0.2);
        item1.setVLRLIQUIDO(8.0);
        item1.setVLRTOTALITEM(48.0);
        item2.setREF("321");
        item2.setDESCRICAO("teste2");
        item2.setQtdeP("30");
        item2.setQtdeM("10");
        item2.setQtdeG("20");
        item2.setQTDE("65");
        item2.setVLRUNIT(10.0);
        item2.setDESCPERC(0.05);
        item2.setVLRLIQUIDO(8.0);
        item2.setVLRTOTALITEM(48.0);
        itens.add(item1);
        itens.add(item2);
        return itens;
    }

    private Map getParametros(String codigo) {
        parameters = new HashMap();
        parameters.put("logo", "src/Relatorios/logo.jpg");
        try {
            st = conexao.getConexao();
            rs = st.executeQuery("select\n"
                    + "p.codpedido,v.nome vendedor,p.datapedido,c.nome razao,c.nomefantasia,c.enderecoent,c.bairroent,\n"
                    + "c.cepent,cid.cidade,cid.estado,c.email,c.fone,c.celular,c.cgccpf,c.inscest,g.descricao pagamento,\n"
                    + "c.contato,p.dataentrega,p.vlracrescimo,p.totalpedido,p.observacao1,p.observacao2,p.observacao3,c.PESSOA_FJ\n"
                    + "from pedidoc p\n"
                    + "inner join cliente c on p.codcliente=c.codcliente\n"
                    + "inner join vendend v on p.codvendedor=v.codvendedor\n"
                    + "inner join cidades cid on c.codcidade=cid.codcidade\n"
                    + "inner join condpag g on p.codprazo=g.codprazo\n"
                    + "where p.codpedido='" + codigo + "' and p.tipopedido='55'");
            while (rs.next()) {
                parameters.put("codpedido", rs.getString("codpedido"));
                parameters.put("vendedor", rs.getString("vendedor"));
                parameters.put("dataPedido", Data.getDataByDate(Data.getDataByTexto(rs.getString("datapedido").replace("-", "/"), "yyyy/MM/dd"), "dd/MM/yyyy"));
                parameters.put("razao", rs.getString("razao"));
                parameters.put("nomeFantasia", rs.getString("nomefantasia"));
                parameters.put("endereco", rs.getString("enderecoent"));
                parameters.put("bairro", rs.getString("bairroent"));
                parameters.put("cep", Mascaras.formataByMascaras("#####-###", rs.getString("cepent")));
                parameters.put("cidade", rs.getString("cidade"));
                parameters.put("uf", rs.getString("estado"));
                parameters.put("email", rs.getString("email"));
                parameters.put("telefone", Mascaras.formataByMascaras("(##) ####-####", rs.getString("fone")));
                parameters.put("celular", Mascaras.formataByMascaras("(##) ####-####", rs.getString("celular")));
                if (rs.getString("PESSOA_FJ").equals("F") == true) {
                    parameters.put("cpf", Mascaras.formataByMascaras("###.###.###-##", rs.getString("cgccpf")));
                } else {
                    parameters.put("cpf", Mascaras.formataByMascaras("##.###.###/####-##", rs.getString("cgccpf")));
                }
                parameters.put("inscest", rs.getString("inscest"));
                parameters.put("pagamento", rs.getString("pagamento"));
                parameters.put("contato", rs.getString("contato"));
                if (rs.getString("dataentrega") != null) {
                    parameters.put("dataEntrega", Data.getDataByDate(Data.getDataByTexto(rs.getString("dataentrega").replace("-", "/"), "yyyy/MM/dd"), "dd/MM/yyyy"));
                }
                parameters.put("vlrAcrescimo", NumberFormat.getCurrencyInstance().format(Double.parseDouble(rs.getString("vlracrescimo"))));
                parameters.put("vlrTotal", NumberFormat.getCurrencyInstance().format(Double.parseDouble(rs.getString("totalpedido"))));
                parameters.put("obs1", rs.getString("observacao1"));
                parameters.put("obs2", rs.getString("observacao2"));
                parameters.put("obs3", rs.getString("observacao3"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os parâmetros do relatório!\n" + e);
        }
        return parameters;
    }
}
