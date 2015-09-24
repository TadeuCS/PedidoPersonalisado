/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Item;
import Util.Conexao;
import Util.Data;
import Util.FixedLengthDocument;
import Util.GeraRelatorios;
import Util.Mascaras;
import java.awt.Event;
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
public class Frm_Principal extends javax.swing.JFrame {

    Statement st;
    Statement st2;
    Statement st3;
    ResultSet rs;
    ResultSet rs2;
    ResultSet rs3;
    Conexao conexao;
    Item item;
    Map parameters;

    public Frm_Principal() {
        initComponents();
        txt_codigo.setDocument(new FixedLengthDocument(8));
        loading.setVisible(false);
        conexao = new Conexao();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        btn_gerar = new javax.swing.JButton();
        loading = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerador Pedido Personalizado");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código do Pedido:");

        txt_codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codigoKeyPressed(evt);
            }
        });

        btn_gerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/carregar.png"))); // NOI18N
        btn_gerar.setText("Gerar");
        btn_gerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(btn_gerar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_gerar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        loading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loading.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loading, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_gerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerarActionPerformed
        if (txt_codigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código do pedido inválido!");
            txt_codigo.requestFocus();
        } else {
            Thread acao;
            acao = new Thread(new Runnable() {
                @Override
                public void run() {
                    loading.setVisible(true);
                    try {
                        GeraRelatorios geraRelatorios = new GeraRelatorios();
                        if (geraRelatorios.imprimirByLista("Rel_PedidoPersonalizado.jasper", getParametros(trataCodigo(txt_codigo.getText())),
                                listItensByPedido(trataCodigo(txt_codigo.getText()))) == false) {
                            geraRelatorios.imprimirByLista("src/Relatorios/Rel_PedidoPersonalizado.jasper", getParametros(trataCodigo(txt_codigo.getText())),
                                    listItensByPedido(trataCodigo(txt_codigo.getText())));
                            loading.setVisible(false);
                        } else {
                            loading.setVisible(false);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            });
            acao.start();

        }
    }//GEN-LAST:event_btn_gerarActionPerformed

    private void txt_codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoKeyPressed
        if (evt.getKeyCode() == Event.ENTER) {
            btn_gerar.doClick();
        }
    }//GEN-LAST:event_txt_codigoKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_gerar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel loading;
    private javax.swing.JTextField txt_codigo;
    // End of variables declaration//GEN-END:variables

    public void geraRelatorio(String codpedido) {
        try {
            GeraRelatorios geraRelatorios = new GeraRelatorios();
            if (geraRelatorios.imprimirByLista("TesteItens.jasper", getParametros(codpedido), listItensByPedido(codpedido)) == false) {
                geraRelatorios.imprimirByLista("src/Relatorios/TesteItens.jasper", getParametros(codpedido), listItensByPedido(codpedido));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private List<Item> listItensByPedido(String codpedido) {
        List<Item> itens = new ArrayList<>();
        try {
            st2 = conexao.getConexao();
            rs2 = st2.executeQuery("select i.codprod,p.REFERENCIA,p.DESCRICAO,i.PRECOUNIT,c.DESCONTOPERC from pedidoi i\n"
                    + "inner join produto p on i.CODPROD=p.CODPROD\n"
                    + "inner join pedidoc c on i.CODPEDIDO=c.CODPEDIDO\n"
                    + "where i.codpedido= '" + codpedido + "' and i.tipopedido='55'\n"
                    + "group by 1,2,3,4,5");

            while (rs2.next()) {
                item = new Item();
                String codprod = rs2.getString("codprod");
                item.setREF(rs2.getString("REFERENCIA"));
                item.setDESCRICAO(rs2.getString("DESCRICAO"));
                item.setVLRUNIT(Double.parseDouble(rs2.getString("precounit")));
                item.setDESCPERC(Double.parseDouble(rs2.getString("descontoperc")) / 100);
                Double vlrliq = Double.parseDouble(rs2.getString("precounit"))
                        - (Double.parseDouble(rs2.getString("precounit"))
                        * Double.parseDouble(rs2.getString("descontoperc")) / 100);
                item.setVLRLIQUIDO(vlrliq);
                item.setQtdeP(retornaQtdeByTamanho(codpedido, codprod, "= 'P'"));
                item.setQtdeM(retornaQtdeByTamanho(codpedido, codprod, "= 'M'"));
                item.setQtdeG(retornaQtdeByTamanho(codpedido, codprod, "= 'G'"));
                item.setQtdeU(retornaQtdeByTamanho(codpedido, codprod, "IS NULL"));
                Integer qtdeTotal = Integer.parseInt(item.getQtdeP())
                        + Integer.parseInt(item.getQtdeM())
                        + Integer.parseInt(item.getQtdeG())
                        + Integer.parseInt(item.getQtdeU());
                item.setQTDE(qtdeTotal + "");
                Double totalItem = qtdeTotal * item.getVLRLIQUIDO();
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
                if (rs3.getString("quantidade") != null) {
                    int i = (int) Double.parseDouble(rs3.getString("quantidade"));
                    qtde = i + "";
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a quantidade de produtos por grade!\n" + e);
        }
        return qtde;
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
        parameters.put("logo", "src/img/logo.jpg");
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
