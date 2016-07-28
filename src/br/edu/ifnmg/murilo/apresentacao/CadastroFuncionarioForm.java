/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.apresentacao;

import br.edu.ifnmg.helder.validaCPF.CPFInvalidoException;
import br.edu.ifnmg.murilo.entidade.Fornecedor;
import br.edu.ifnmg.murilo.entidade.Funcionario;
import br.edu.ifnmg.murilo.excecao.CampoObrigatorioException;
import br.edu.ifnmg.murilo.excecao.SistemaControleProducaoException;
import br.edu.ifnmg.murilo.negocio.FornecedorBO;
import br.edu.ifnmg.murilo.negocio.FuncionarioBO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author murilo
 */
public class CadastroFuncionarioForm extends javax.swing.JFrame {

    private Funcionario funcionario;
    private boolean flagFuncionarioParaEdicao = false;
    private ConsultarFuncionariosForm consultarFuncionariosForm;
    // private ConsultarFornecedoresForm exibirFornecedoresForm;

    private SimpleDateFormat formatador
            = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form FornecedorForm
     */
    public CadastroFuncionarioForm(ConsultarFuncionariosForm consultarFuncionariosForm) {
        this.consultarFuncionariosForm = consultarFuncionariosForm;

        this.funcionario = new Funcionario();
        this.preparaTela();

    }

    public CadastroFuncionarioForm(ConsultarFuncionariosForm consultarFuncionariosForm, Funcionario funcionarioParaEdicao) {
        this.consultarFuncionariosForm = consultarFuncionariosForm;

        this.funcionario = funcionarioParaEdicao;
        this.preparaTela();
        this.inicializarCamposTela();
        this.flagFuncionarioParaEdicao = true;
        this.txtCpf.setEnabled(false);

    }

    private void inicializarCamposTela() {
        txtBairro.setText(funcionario.getBairro());
        txtCelular.setText(funcionario.getCelular());
        txtCep.setText(funcionario.getCep());
        txtCidade.setText(funcionario.getCidade());
        txtComplemento.setText(funcionario.getComplemento());
        txtCpf.setText(funcionario.getCpf());
        txtEmail.setText(funcionario.getEmail());
        txtLogradoro.setText(funcionario.getLogradouro());
        txtNome.setText(funcionario.getNome());
        txtNumero.setText(funcionario.getNumero());
        txtObservacoes.setText(funcionario.getObservacao());
        txtTelefone.setText(funcionario.getTelefone());

        String dataAdmissao
                = formatador.format(funcionario.getDataAdmissao());
        txtDataAdmissao.setText(dataAdmissao);

        String dataNascimento
                = formatador.format(funcionario.getDataNascimento());
        txtDataNascimento.setText(dataNascimento);

        cbxUf.setSelectedItem(funcionario.getUf());
    }

    public CadastroFuncionarioForm() {
        this.funcionario = new Funcionario();
        this.preparaTela();
    }

    private void preparaTela() {
        try {
            this.initComponents();
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlFornecedor = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblLogradouro = new javax.swing.JLabel();
        txtLogradoro = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbxUf = new javax.swing.JComboBox();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblCpf = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar1 = new javax.swing.JButton();
        txtCelular = new javax.swing.JFormattedTextField();
        txtTelefone = new javax.swing.JFormattedTextField();
        lblCep = new javax.swing.JLabel();
        txtCep = new javax.swing.JFormattedTextField();
        txtCpf = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDataAdmissao = new javax.swing.JFormattedTextField();
        lblDataNascimento = new javax.swing.JLabel();
        txtDataNascimento = new javax.swing.JFormattedTextField();
        lblObservacoes = new javax.swing.JLabel();
        txtObservacoes = new javax.swing.JTextField();

        setTitle("Cadastro de Funcionários");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(800, 480));
        setResizable(false);

        pnlFornecedor.setBorder(javax.swing.BorderFactory.createTitledBorder("Funcionário"));
        pnlFornecedor.setPreferredSize(new java.awt.Dimension(681, 502));

        lblNome.setText("Nome*:");

        lblLogradouro.setText("Logradouro*:");

        lblNumero.setText("Numero*:");

        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });

        lblBairro.setText("Bairro*:");

        lblCidade.setText("Cidade*:");

        lblComplemento.setText("Compelemento:");

        jLabel1.setText("UF*:");

        cbxUf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PR", "PB", "PA", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SE", "SP", "TO" }));

        lblEmail.setText("Email*:");

        lblCpf.setText("CPF*:");

        lblTelefone.setText("Telefone*:");

        jLabel2.setText("Celular:");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/disk.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/cancel.png"))); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblCep.setText("CEP*:");

        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel3.setText("Data Admissão*:");

        try {
            txtDataAdmissao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblDataNascimento.setText("Data Nascimento*:");

        try {
            txtDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataNascimentoActionPerformed(evt);
            }
        });

        lblObservacoes.setText("Obs:");

        javax.swing.GroupLayout pnlFornecedorLayout = new javax.swing.GroupLayout(pnlFornecedor);
        pnlFornecedor.setLayout(pnlFornecedorLayout);
        pnlFornecedorLayout.setHorizontalGroup(
            pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome)
                    .addGroup(pnlFornecedorLayout.createSequentialGroup()
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBairro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                                .addComponent(lblCidade)
                                .addGap(0, 298, Short.MAX_VALUE))
                            .addComponent(txtCidade))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(pnlFornecedorLayout.createSequentialGroup()
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                                .addComponent(lblLogradouro)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtLogradoro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblComplemento, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtComplemento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumero, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNumero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFornecedorLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFornecedorLayout.createSequentialGroup()
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCep)
                            .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                                .addComponent(lblCpf)
                                .addGap(156, 156, 156))
                            .addComponent(txtCpf)))
                    .addGroup(pnlFornecedorLayout.createSequentialGroup()
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefone)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(36, 36, 36)
                                .addComponent(lblDataNascimento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblObservacoes)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                                .addComponent(txtDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtObservacoes)))))
                .addContainerGap())
        );
        pnlFornecedorLayout.setVerticalGroup(
            pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogradouro)
                    .addComponent(lblNumero)
                    .addComponent(lblComplemento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLogradoro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblBairro)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(lblCpf)
                    .addComponent(lblCep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFornecedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefone)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(lblDataNascimento)
                            .addComponent(lblObservacoes))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtObservacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFornecedorLayout.createSequentialGroup()
                        .addGap(35, 154, Short.MAX_VALUE)
                        .addGroup(pnlFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (this.flagFuncionarioParaEdicao == false) {
            this.salvarFornecedor();
        } else {
            this.editarFuncionario();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataNascimentoActionPerformed

    private void editarFuncionario() {

        try {
            this.verificaCamposObrigatorios();
            this.recuperaCamposTela();
            FuncionarioBO funcionarioBO = new FuncionarioBO();
            funcionarioBO.atualizar(funcionario);

            JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!", "Cadastro de funcionário", JOptionPane.INFORMATION_MESSAGE);

            this.consultarFuncionariosForm.carregarTabelaFuncionarios();
            dispose();
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de fornecedor", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void salvarFornecedor() {
        try {
            this.verificaCamposObrigatorios();
            this.recuperaCamposTela();
            FuncionarioBO funcionarioBO = new FuncionarioBO();
            funcionarioBO.inserir(funcionario);

            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!", "Cadastro de funcionário", JOptionPane.INFORMATION_MESSAGE);
            this.limpaCamposTela();
            if (consultarFuncionariosForm != null) {
                this.consultarFuncionariosForm.carregarTabelaFuncionarios();
            }
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
        } catch (CPFInvalidoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
        }catch (ParseException e) {
            String mensagem = "Erro ao realizar operção:\nInfome uma data válida!";
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void recuperaCamposTela() throws ParseException {
        funcionario.setBairro(txtBairro.getText());
        funcionario.setCelular(txtCelular.getText());
        funcionario.setCep(txtCep.getText());
        funcionario.setCidade(txtCidade.getText());
        funcionario.setComplemento(txtComplemento.getText());
        funcionario.setCpf(txtCpf.getText());
        funcionario.setEmail(txtEmail.getText());
        funcionario.setLogradouro(txtLogradoro.getText());
        funcionario.setNome(txtNome.getText());
        funcionario.setNumero(txtNumero.getText());
        funcionario.setObservacao(txtObservacoes.getText());
        funcionario.setUf(cbxUf.getSelectedItem().toString());

        funcionario.setTelefone(txtTelefone.getText());
        funcionario.setDataAdmissao(formatador.parse(txtDataAdmissao.getText()));
        funcionario.setDataNascimento(formatador.parse(txtDataNascimento.getText()));

    }

    private void verificaCamposObrigatorios() {
        if (txtBairro.getText().trim().isEmpty()
                || txtCep.getText().trim().isEmpty()
                || txtCidade.getText().trim().isEmpty()
                || txtCpf.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty()
                || txtNome.getText().trim().isEmpty()
                || txtLogradoro.getText().trim().isEmpty()
                || txtNumero.getText().trim().isEmpty()
                || txtTelefone.getText().trim().isEmpty()
                || txtDataAdmissao.getText().equals("  /  /")
                || txtDataNascimento.getText().equals("  /  /")
                || cbxUf.getSelectedIndex() == 0) {
            throw new CampoObrigatorioException();
        }
    }

    private void limpaCamposTela() {
        txtBairro.setText("");
        txtCelular.setText("");
        txtCep.setText("");
        txtCidade.setText("");
        txtCpf.setText("");
        txtComplemento.setText("");
        txtEmail.setText("");
        txtNome.setText("");
        txtLogradoro.setText("");
        txtNumero.setText("");
        txtObservacoes.setText("");
        txtTelefone.setText("");
        cbxUf.setSelectedIndex(0);
        txtDataAdmissao.setText("");
        txtDataNascimento.setText("");
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxUf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblDataNascimento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblLogradouro;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblObservacoes;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JPanel pnlFornecedor;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtDataAdmissao;
    private javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogradoro;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtObservacoes;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables

}
