/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.apresentacao;

import br.edu.ifnmg.murilo.entidade.Producao;
import br.edu.ifnmg.murilo.entidade.Tijolo;
import br.edu.ifnmg.murilo.excecao.CampoObrigatorioException;
import br.edu.ifnmg.murilo.excecao.CampoVazioException;
import br.edu.ifnmg.murilo.excecao.SistemaControleProducaoException;
import br.edu.ifnmg.murilo.negocio.TijoloBO;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author murilo
 */
public class CadastroTijoloForm extends javax.swing.JFrame {

    private Tijolo tijolo;
    private ConsultarTijoloForm consultarTijoloForm;
    private boolean flagTijoloEdicao = false;
    private TelaProducaoForm telaProducaoForm;
    /**
     * Creates new form CadastroTijoloForm
     */
    public CadastroTijoloForm() {
        tijolo = new Tijolo();
        this.preparaTela();
    }

    CadastroTijoloForm(ConsultarTijoloForm consultarTijoloForm) {
        tijolo = new Tijolo();
        this.consultarTijoloForm = consultarTijoloForm;
        this.preparaTela();
    }

    CadastroTijoloForm(ConsultarTijoloForm consultarTijoloForm, Tijolo tijoloParaEdicao) {
        tijolo = tijoloParaEdicao;
        this.consultarTijoloForm = consultarTijoloForm;
        this.flagTijoloEdicao = true;
        this.preparaTela();
        this.inicializarCamposTela();
    }

    private void preparaTela() {
        try {
            this.initComponents();
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de materia prima", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void validarCamposObrigatorio() {
        if (txtDescricao.getText().trim().isEmpty()
                || txtDimensoes.getText().trim().isEmpty()
                || txtTipo.getText().trim().isEmpty()
                || txtValorDeVendaMilheiro.getText().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }
    }

    private void inicializarCamposTela() {
        txtDescricao.setText(tijolo.getDescricao());
        txtDimensoes.setText(tijolo.getDimensoes());
        txtObs.setText(tijolo.getObservacoes());
        txtTipo.setText(tijolo.getTipo());
        txtValorDeVendaMilheiro.setText(String.valueOf(tijolo.getValorVenda()));
    }

    private void recuperaCamposTela() {
        this.tijolo.setDescricao(txtDescricao.getText());
        this.tijolo.setDimensoes(txtDimensoes.getText());
        this.tijolo.setObservacoes(txtObs.getText());
        this.tijolo.setTipo(txtTipo.getText());
        
        
        
        DecimalFormat formatador = new DecimalFormat("#,##0.00");
        try {                   
            double valorUnitario = formatador.parse(txtValorDeVendaMilheiro.getText()).doubleValue();
            this.tijolo.setValorVenda(valorUnitario);
        } catch (ParseException ex) {
            Logger.getLogger(CadastroTijoloForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void limpaCamposTela() {
        txtDescricao.setText("");
        txtDimensoes.setText("");
        txtObs.setText("");
        txtTipo.setText("");
    }

    private void salvarTijolo() {
        try {
            this.validarCamposObrigatorio();
            this.recuperaCamposTela();

            TijoloBO tijoloBO = new TijoloBO();
            tijoloBO.inserir(tijolo);

            JOptionPane.showMessageDialog(this, "Tijolo cadastrado com sucesso!", "Cadastro de Tijolo", JOptionPane.INFORMATION_MESSAGE);
            this.limpaCamposTela();
            if (consultarTijoloForm != null) {
                this.consultarTijoloForm.carregarTabelaTijolos();
            }
            if(this.telaProducaoForm.isEnabled()){
                telaProducaoForm.carregarComboTijolo();
            }
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de aluno", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de Tijolo", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void editarTijolo() {
        try {
            this.validarCamposObrigatorio();
            this.recuperaCamposTela();

            TijoloBO tijoloBO = new TijoloBO();
            tijoloBO.atualizar(tijolo);

            JOptionPane.showMessageDialog(this, "Tijolo editado com sucesso!", "Cadastro de Tijolo", JOptionPane.INFORMATION_MESSAGE);
            // this.limpaCamposTela();
            this.consultarTijoloForm.carregarTabelaTijolos();
            dispose();
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de fonecedor", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTipo = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDimensoes = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        lblObs = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtValorDeVendaMilheiro = new javax.swing.JFormattedTextField();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Tijolos");
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tijolo"));

        lblTipo.setText("Tipo*:");

        jLabel1.setText("Dimensões (LxAxC)*:");

        lblDescricao.setText("Descrição*: ");

        lblObs.setText("Observação:");

        jLabel2.setText("Preço de Venda do Milheiro*:");

        txtValorDeVendaMilheiro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/disk.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblObs)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblTipo)
                                        .addGap(0, 567, Short.MAX_VALUE))
                                    .addComponent(txtTipo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDimensoes)))
                            .addComponent(txtObs)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblDescricao)
                                        .addGap(0, 472, Short.MAX_VALUE))
                                    .addComponent(txtDescricao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtValorDeVendaMilheiro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDimensoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorDeVendaMilheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblObs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (flagTijoloEdicao == false) {
            this.salvarTijolo();
        } else {
            this.editarTijolo();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDimensoes;
    private javax.swing.JTextField txtObs;
    private javax.swing.JTextField txtTipo;
    private javax.swing.JFormattedTextField txtValorDeVendaMilheiro;
    // End of variables declaration//GEN-END:variables
}
