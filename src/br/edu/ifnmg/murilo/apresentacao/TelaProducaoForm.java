/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.apresentacao;

import br.edu.ifnmg.murilo.entidade.Funcionario;
import br.edu.ifnmg.murilo.entidade.Producao;
import br.edu.ifnmg.murilo.entidade.Tarefa;
import br.edu.ifnmg.murilo.entidade.FuncionarioTarefaProducao;
import br.edu.ifnmg.murilo.entidade.GastosExtra;
import br.edu.ifnmg.murilo.entidade.Tijolo;
import br.edu.ifnmg.murilo.excecao.CampoDeVendaInvalidoException;
import br.edu.ifnmg.murilo.excecao.CampoExtraInvalidoException;
import br.edu.ifnmg.murilo.excecao.CampoObrigatorioException;
import br.edu.ifnmg.murilo.excecao.CampoValorDiariaNegativoException;
import br.edu.ifnmg.murilo.excecao.CampoGastoExtraJaInseridoException;
import br.edu.ifnmg.murilo.excecao.CampoGastoMateriaPrimaJaInseridoException;
import br.edu.ifnmg.murilo.excecao.CampoProducaoJaInseridoException;
import br.edu.ifnmg.murilo.excecao.ComboBoxInvalidaException;
import br.edu.ifnmg.murilo.excecao.GastoNegativoMateriaPrimaException;
import br.edu.ifnmg.murilo.excecao.GastoTotalInvalidoExcpetion;
import br.edu.ifnmg.murilo.excecao.SistemaControleProducaoException;
import br.edu.ifnmg.murilo.negocio.FuncionarioBO;
import br.edu.ifnmg.murilo.negocio.GastosExtraBO;
import br.edu.ifnmg.murilo.negocio.ProducaoBO;
import br.edu.ifnmg.murilo.negocio.TarefaBO;
import br.edu.ifnmg.murilo.negocio.TijoloBO;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author murilo
 */
public class TelaProducaoForm extends javax.swing.JInternalFrame {

    private List<Tarefa> tarefas;
    private List<Funcionario> funcionarios;
    private List<FuncionarioTarefaProducao> tarefaFuncionarios;
    private List<Tijolo> tijolos;
    private FuncionarioTarefaProducao tarefaFuncionario;
    private double somaGastoTotal;
    private double lucroEstimado;
    private double valorVenda;
    private Producao producao;
    private String formato = "R$ #,##0.00";
    private DecimalFormat preco = new DecimalFormat(formato);
    private SimpleDateFormat formatador
            = new SimpleDateFormat("dd/MM/yyyy");
    private GastosExtra gastosExtra;
    private boolean flagProducao;
    private double somaMateriaPrima;
    private double somaGastosExtras;
    private double somaGastosFuncionarios;

    /**
     * Creates new form ProducaoFormTeste
     */
    public TelaProducaoForm() {
        preco.format(somaGastoTotal);
        producao = new Producao();
        this.flagProducao = false;
        this.preparaTela();
    }

    private void preparaTela() {
        try {
            this.initComponents();
            lblSomaGastoTotal.setText("R$ 0");
            lblSomaLucroEstimado.setText("R$ 0");
            lblValorDeVenda.setText("R$ 0");
            txtGastoBarro.setText("0");
            txtGastoLenha.setText("0");
            txtValorDiaria.setText("0");
            lblValorAgua.setText("R$ 0");
            txtQuantidadeMilheiro.setText("0");
            txtGastosExtras.setText("0");
            lblValorEletricidade.setText("R$ 0");
            this.tarefaFuncionarios = new ArrayList<>();
            this.carregarTabelaTarefasFuncionario();
            this.carregarComboFuncionarios();
            this.carregarComboTarefas();
            this.carregarComboTijolo();

        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Fornecedores Cadastrados", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void carregarTabelaTarefasFuncionario() throws SQLException {

        ModeloTabelaFornecedor modelo = new ModeloTabelaFornecedor();
        tblResultado.setModel(modelo);
    }

    private void carregarComboFuncionarios() throws SQLException {
        FuncionarioBO funcionarioBO = new FuncionarioBO();
        this.funcionarios = funcionarioBO.buscarTodos();

        for (Funcionario funcionario : funcionarios) {
            cmbFuncionario.addItem(funcionario.getNome());
        }

    }

    public void carregarComboTijolo() throws SQLException {
        TijoloBO tijoloBO = new TijoloBO();
        this.tijolos = tijoloBO.buscarTodos();

        for (Tijolo tijolo : tijolos) {
            cmbTijolo.addItem(tijolo.getTipo());
        }

    }

    private void carregarComboTarefas() throws SQLException {
        TarefaBO tarefaBO = new TarefaBO();
        this.tarefas = tarefaBO.buscarTodos();

        for (Tarefa tarefa : tarefas) {
            cmbTarefa.addItem(tarefa.getNome());
        }
    }

    private void selecionarFuncionarios() throws SQLException {
        try {
            this.verificaCampoVenda();

            int posicaoSelecionadaTarefa = cmbTarefa.getSelectedIndex();
            this.verificarComboBoxValida(posicaoSelecionadaTarefa);
            int posicaoSelecionadaFuncionario = cmbFuncionario.getSelectedIndex();
            this.verificarComboBoxValida(posicaoSelecionadaFuncionario);

            TarefaBO tarefaBO = new TarefaBO();
            this.tarefas = tarefaBO.buscarTodos();
            Tarefa tarefaSelecionada = tarefas.get(posicaoSelecionadaTarefa - 1);

            FuncionarioBO funcionarioBO = new FuncionarioBO();
            Funcionario funcionarioSelecionado = funcionarios.get(posicaoSelecionadaFuncionario - 1);

            String strValorDiaria = txtValorDiaria.getText().trim();

            if (strValorDiaria.isEmpty()) {
                throw new CampoObrigatorioException();
            }
            DecimalFormat formatador = new DecimalFormat("#,##0.00");
            double valorDiaria = formatador.parse(strValorDiaria).doubleValue();

            if (valorDiaria <= 0) {
                throw new CampoValorDiariaNegativoException();
            }

            this.somaGastosFuncionarios = this.somaGastosFuncionarios + (valorDiaria);
            this.setGastoTotal();
            this.calculaEstimativaDeLucro();

            FuncionarioTarefaProducao funcionarioTarefaProducao = new FuncionarioTarefaProducao();
            funcionarioTarefaProducao.setNomeFuncionario(funcionarioSelecionado.getNome());
            funcionarioTarefaProducao.setNomeTarefa(tarefaSelecionada.getNome());
            funcionarioTarefaProducao.setValorDiaria(valorDiaria);
            funcionarioTarefaProducao.setFuncionario(funcionarioSelecionado);
            funcionarioTarefaProducao.setTarefa(tarefaSelecionada);

            this.tarefaFuncionarios.add(funcionarioTarefaProducao);

            this.carregarTabelaTarefasFuncionario();
        } catch (Exception e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de Funcionários", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void excluirFuncionario() {
        int linhaSelecionada = tblResultado.getSelectedRow();
        if (linhaSelecionada != -1) {

            Double valor = 0.0;
            valor = this.tarefaFuncionarios.get(linhaSelecionada).getValorDiaria();

            this.somaGastosFuncionarios = this.somaGastosFuncionarios - valor;
            this.calculaEstimativaDeLucroAoExcluir(valor);
            this.setGastoTotal();

            this.tarefaFuncionarios.remove(linhaSelecionada);
            try {
                this.carregarTabelaTarefasFuncionario();
            } catch (SQLException ex) {
                Logger.getLogger(TelaProducaoForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void calculaEstimativaDeLucroAoExcluir(double valor) {
        this.lucroEstimado = this.lucroEstimado + valor;
        this.lblSomaLucroEstimado.setText(this.preco.format(lucroEstimado));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlProducao = new javax.swing.JPanel();
        lblDataFinal = new javax.swing.JLabel();
        txtDataProducao = new javax.swing.JFormattedTextField();
        lblTijoloProduzido = new javax.swing.JLabel();
        cmbTijolo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtQuantidadeMilheiro = new javax.swing.JFormattedTextField();
        btnAdicionarProducao = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        pnlMateiraPrima = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGastoLenha = new javax.swing.JFormattedTextField();
        txtGastoBarro = new javax.swing.JFormattedTextField();
        btnAdicionarGastoMateriaPrima = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbTarefa = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cmbFuncionario = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtValorDiaria = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        btnAdicionarItemTabela = new javax.swing.JButton();
        btnExcluirItemFuncionario = new javax.swing.JButton();
        pnlGastosExtras = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lbllLeituraInicial = new javax.swing.JLabel();
        lblLeituraFinal = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtLeituraIncialEletricidade = new javax.swing.JFormattedTextField();
        txtLeituraFinalEletricidade = new javax.swing.JFormattedTextField();
        txtLeituraInicialAgua = new javax.swing.JFormattedTextField();
        txtLeituraFinalAgua = new javax.swing.JFormattedTextField();
        lblOutros = new javax.swing.JLabel();
        lblValorOutros = new javax.swing.JLabel();
        txtGastosExtras = new javax.swing.JFormattedTextField();
        btnAdicionarGastosExtras = new javax.swing.JButton();
        lblValorEletricidade = new javax.swing.JLabel();
        lblValorAgua = new javax.swing.JLabel();
        lblGasto = new javax.swing.JLabel();
        btnSalvarProducao = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        pnlValorVenda = new javax.swing.JPanel();
        lblValorDeVenda = new javax.swing.JLabel();
        pnlLucroEstimado = new javax.swing.JPanel();
        lblSomaLucroEstimado = new javax.swing.JLabel();
        pnlGastoTotal = new javax.swing.JPanel();
        lblSomaGastoTotal = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Cadrastro de Produção");

        pnlProducao.setBorder(javax.swing.BorderFactory.createTitledBorder("Produção"));

        lblDataFinal.setText("Data Produção*:");

        try {
            txtDataProducao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblTijoloProduzido.setText("Tijolo Produzido*:");

        cmbTijolo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        jLabel1.setText("Quantidade de milheiro*:");

        txtQuantidadeMilheiro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        btnAdicionarProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/arrow_refresh.png"))); // NOI18N
        btnAdicionarProducao.setText("Atualizar");
        btnAdicionarProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarProducaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlProducaoLayout = new javax.swing.GroupLayout(pnlProducao);
        pnlProducao.setLayout(pnlProducaoLayout);
        pnlProducaoLayout.setHorizontalGroup(
            pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProducaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProducaoLayout.createSequentialGroup()
                        .addGroup(pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDataProducao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTijolo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlProducaoLayout.createSequentialGroup()
                                .addComponent(lblTijoloProduzido)
                                .addGap(0, 198, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantidadeMilheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(78, 78, 78))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProducaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdicionarProducao)))
                .addContainerGap())
        );
        pnlProducaoLayout.setVerticalGroup(
            pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProducaoLayout.createSequentialGroup()
                .addGroup(pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataFinal)
                    .addComponent(lblTijoloProduzido)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTijolo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidadeMilheiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnAdicionarProducao)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Gastos"));

        pnlMateiraPrima.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Matéria Prima")));

        jLabel2.setText("Lenha*:");

        jLabel5.setText("Valor:");

        jLabel7.setText("Barro*:");

        txtGastoLenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGastoLenhaActionPerformed(evt);
            }
        });

        btnAdicionarGastoMateriaPrima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/arrow_refresh.png"))); // NOI18N
        btnAdicionarGastoMateriaPrima.setText("Atualizar");
        btnAdicionarGastoMateriaPrima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarGastoMateriaPrimaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMateiraPrimaLayout = new javax.swing.GroupLayout(pnlMateiraPrima);
        pnlMateiraPrima.setLayout(pnlMateiraPrimaLayout);
        pnlMateiraPrimaLayout.setHorizontalGroup(
            pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMateiraPrimaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGastoBarro, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(txtGastoLenha, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(282, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMateiraPrimaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionarGastoMateriaPrima)
                .addContainerGap())
        );
        pnlMateiraPrimaLayout.setVerticalGroup(
            pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMateiraPrimaLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(7, 7, 7)
                .addGroup(pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtGastoLenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(pnlMateiraPrimaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtGastoBarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(btnAdicionarGastoMateriaPrima)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Funcionário"));

        jLabel6.setText("Tarefa:");

        cmbTarefa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        jLabel8.setText("Funcionário:");

        cmbFuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione" }));

        jLabel9.setText("Valor Diária*:");

        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblResultado);

        btnAdicionarItemTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/arrow_refresh.png"))); // NOI18N
        btnAdicionarItemTabela.setText("Atualizar");
        btnAdicionarItemTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarItemTabelaActionPerformed(evt);
            }
        });

        btnExcluirItemFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/cancel.png"))); // NOI18N
        btnExcluirItemFuncionario.setText("Excluir");
        btnExcluirItemFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbFuncionario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbTarefa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtValorDiaria, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdicionarItemTabela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluirItemFuncionario)
                        .addGap(7, 7, 7)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTarefa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorDiaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarItemTabela)
                    .addComponent(btnExcluirItemFuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlGastosExtras.setBorder(javax.swing.BorderFactory.createTitledBorder("Outros"));

        jLabel12.setText("Eletricidade:");

        lbllLeituraInicial.setText("Leitura Inicial*");

        lblLeituraFinal.setText("Leitura Final*");

        jLabel15.setText("Água");

        txtLeituraIncialEletricidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtLeituraIncialEletricidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLeituraIncialEletricidadeActionPerformed(evt);
            }
        });

        txtLeituraFinalEletricidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtLeituraInicialAgua.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtLeituraFinalAgua.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        lblOutros.setText("Outros:");

        lblValorOutros.setText("Valor:");

        txtGastosExtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGastosExtrasActionPerformed(evt);
            }
        });

        btnAdicionarGastosExtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/arrow_refresh.png"))); // NOI18N
        btnAdicionarGastosExtras.setText("Atualizar");
        btnAdicionarGastosExtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarGastosExtrasActionPerformed(evt);
            }
        });

        lblValorEletricidade.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N

        lblValorAgua.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N

        lblGasto.setText("Gasto");

        javax.swing.GroupLayout pnlGastosExtrasLayout = new javax.swing.GroupLayout(pnlGastosExtras);
        pnlGastosExtras.setLayout(pnlGastosExtrasLayout);
        pnlGastosExtrasLayout.setHorizontalGroup(
            pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                        .addComponent(lblOutros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                                .addComponent(lblValorOutros)
                                .addContainerGap())
                            .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                                .addComponent(txtGastosExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAdicionarGastosExtras))))
                    .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                        .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbllLeituraInicial)
                            .addComponent(txtLeituraInicialAgua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLeituraIncialEletricidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtLeituraFinalAgua, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                .addComponent(txtLeituraFinalEletricidade))
                            .addComponent(lblLeituraFinal))
                        .addGap(18, 18, 18)
                        .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblValorAgua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                                .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblValorEletricidade, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGasto))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        pnlGastosExtrasLayout.setVerticalGroup(
            pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGastosExtrasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbllLeituraInicial)
                    .addComponent(lblLeituraFinal)
                    .addComponent(lblGasto))
                .addGap(4, 4, 4)
                .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtLeituraIncialEletricidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLeituraFinalEletricidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorEletricidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(lblValorAgua)
                    .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtLeituraInicialAgua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtLeituraFinalAgua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblValorOutros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlGastosExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGastosExtras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOutros)
                    .addComponent(btnAdicionarGastosExtras))
                .addContainerGap())
        );

        btnSalvarProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/disk.png"))); // NOI18N
        btnSalvarProducao.setText("Salvar");
        btnSalvarProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarProducaoActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifnmg/murilo/apresentacao/imagens/cancel.png"))); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlGastosExtras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlMateiraPrima, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvarProducao, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlMateiraPrima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlGastosExtras, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvarProducao, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlValorVenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Valor de Venda"));

        lblValorDeVenda.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        lblValorDeVenda.setForeground(new java.awt.Color(1, 1, 1));

        javax.swing.GroupLayout pnlValorVendaLayout = new javax.swing.GroupLayout(pnlValorVenda);
        pnlValorVenda.setLayout(pnlValorVendaLayout);
        pnlValorVendaLayout.setHorizontalGroup(
            pnlValorVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlValorVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblValorDeVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );
        pnlValorVendaLayout.setVerticalGroup(
            pnlValorVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlValorVendaLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblValorDeVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLucroEstimado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Lucro Estimado")));

        lblSomaLucroEstimado.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        lblSomaLucroEstimado.setForeground(new java.awt.Color(28, 38, 228));

        javax.swing.GroupLayout pnlLucroEstimadoLayout = new javax.swing.GroupLayout(pnlLucroEstimado);
        pnlLucroEstimado.setLayout(pnlLucroEstimadoLayout);
        pnlLucroEstimadoLayout.setHorizontalGroup(
            pnlLucroEstimadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLucroEstimadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSomaLucroEstimado, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLucroEstimadoLayout.setVerticalGroup(
            pnlLucroEstimadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLucroEstimadoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblSomaLucroEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGastoTotal.setBorder(javax.swing.BorderFactory.createTitledBorder("Gasto Total"));

        lblSomaGastoTotal.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        lblSomaGastoTotal.setForeground(new java.awt.Color(226, 36, 46));

        javax.swing.GroupLayout pnlGastoTotalLayout = new javax.swing.GroupLayout(pnlGastoTotal);
        pnlGastoTotal.setLayout(pnlGastoTotalLayout);
        pnlGastoTotalLayout.setHorizontalGroup(
            pnlGastoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGastoTotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSomaGastoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );
        pnlGastoTotalLayout.setVerticalGroup(
            pnlGastoTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGastoTotalLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblSomaGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlGastoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlLucroEstimado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlProducao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLucroEstimado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGastoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlValorVenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarItemTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarItemTabelaActionPerformed
        try {
            // TODO add your handling code here:
            this.selecionarFuncionarios();
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(TelaProducaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAdicionarItemTabelaActionPerformed

    private void btnExcluirItemFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemFuncionarioActionPerformed
        // TODO add your handling code here:
        this.excluirFuncionario();
    }//GEN-LAST:event_btnExcluirItemFuncionarioActionPerformed

    private void txtGastoLenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGastoLenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGastoLenhaActionPerformed

    private void btnAdicionarGastoMateriaPrimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarGastoMateriaPrimaActionPerformed
        try {
            this.recuperarCamposMateriaPrima();
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarGastoMateriaPrimaActionPerformed

    private void txtLeituraIncialEletricidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLeituraIncialEletricidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLeituraIncialEletricidadeActionPerformed

    private void txtGastosExtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGastosExtrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGastosExtrasActionPerformed

    private void btnSalvarProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarProducaoActionPerformed
        // TODO add your handling code here:
        this.salvarProducao();
    }//GEN-LAST:event_btnSalvarProducaoActionPerformed

    private void btnAdicionarGastosExtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarGastosExtrasActionPerformed
        try {
            this.recuparCamposGastosExtrasESetaValores();
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarGastosExtrasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAdicionarProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarProducaoActionPerformed
        try {
            // TODO add your handling code here:
            this.recuperaCamposTijolo();
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            String mensagem = "Erro ao realizar operção:\nInfome uma data válida!";
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + ex.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de funcionário", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_btnAdicionarProducaoActionPerformed

    private void verificaCampoVenda() {
        if (lblValorDeVenda.getText().equals("R$ 0")) {
            throw new CampoDeVendaInvalidoException();
        }
    }

    private void verificaCamposObrigatorios() {
        if (txtLeituraFinalAgua.getText().trim().isEmpty()
                || txtLeituraFinalEletricidade.getText().trim().isEmpty()
                || txtLeituraIncialEletricidade.getText().trim().isEmpty()
                || txtLeituraInicialAgua.getText().trim().isEmpty()
                || txtGastoBarro.getText().trim().isEmpty()
                || txtGastoLenha.getText().trim().isEmpty()
                || txtValorDiaria.getText().trim().isEmpty()
                || txtQuantidadeMilheiro.getText().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }
    }

    private void recuparCamposGastosExtrasESetaValores() {
        this.verificaCampoVenda();

        double leituraInicialAgua;
        double leituraFinalAgua;
        double leituraInicialEletricidade;
        double leituraFinalEletricidade;
        double gastosExtras;

        String strGastosExtras;
        String strLeituraFinalAgua = txtLeituraFinalAgua.getText().trim();
        String strLeituraFinalEletricidade = txtLeituraFinalEletricidade.getText().trim();
        String strLeituraInicialAgua = txtLeituraInicialAgua.getText().trim();
        String strLeituraInicialEletricidade = txtLeituraIncialEletricidade.getText().trim();
        strGastosExtras = txtGastosExtras.getText().trim();
        strGastosExtras = strGastosExtras.replace(",", ".");

        if (strLeituraFinalAgua.isEmpty()
                || strLeituraFinalEletricidade.isEmpty()
                || strLeituraInicialAgua.isEmpty()
                || strLeituraInicialEletricidade.isEmpty()) {
            throw new CampoObrigatorioException();
        }

        leituraFinalAgua = Double.parseDouble(strLeituraFinalAgua);
        leituraFinalEletricidade = Double.parseDouble(strLeituraFinalEletricidade);
        leituraInicialAgua = Double.parseDouble(strLeituraInicialAgua);
        leituraInicialEletricidade = Double.parseDouble(strLeituraInicialEletricidade);

        if (!strGastosExtras.trim().isEmpty()) {
            gastosExtras = Double.parseDouble(strGastosExtras);
        } else {
            gastosExtras = 0;
        }

        if (leituraFinalAgua <= 0
                || leituraFinalEletricidade <= 0
                || leituraInicialAgua <= 0
                || leituraInicialEletricidade <= 0
                || gastosExtras <= 0) {
            throw new CampoExtraInvalidoException();
        }

        if (leituraInicialAgua >= leituraFinalAgua || leituraInicialEletricidade >= leituraFinalEletricidade) {
            throw new CampoExtraInvalidoException();
        }

        this.buscarValorGastosExtras();

        double gastoAgua = (leituraFinalAgua - leituraInicialAgua) * gastosExtra.getTarifaAgua();
        double gastoEletricidade = (leituraFinalEletricidade - leituraInicialEletricidade) * gastosExtra.getTarifaLuz();

        this.lblValorAgua.setText(preco.format(gastoAgua));
        this.lblValorEletricidade.setText(preco.format(gastoEletricidade));

        this.producao.setGastoAgua(gastoAgua);
        this.producao.setGastoEletricidade(gastoEletricidade);
        this.producao.setGastoOutros(gastosExtras);
        this.somaGastosExtras = gastoAgua + gastoEletricidade + gastosExtras;
        this.setGastoTotal();
        this.calculaEstimativaDeLucro();

        //   this.flagGastosExtras = true;
    }

    private void buscarValorGastosExtras() {
        GastosExtraBO gastosExtraBO = new GastosExtraBO();
        try {
            this.gastosExtra = gastosExtraBO.buscar();
        } catch (SQLException ex) {
            Logger.getLogger(TelaProducaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setGastoTotal() {
        this.somaGastoTotal = this.somaGastosExtras + this.somaMateriaPrima + this.somaGastosFuncionarios;
        this.lblSomaGastoTotal.setText(preco.format(somaGastoTotal));
    }

    private void verificarComboBoxValida(int posicaoSelecionada) {
        if (posicaoSelecionada == 0) {
            throw new ComboBoxInvalidaException();
        }
    }

    private void recuperaCamposTijolo() throws SQLException, ParseException {
        int posicaoSelecionada = cmbTijolo.getSelectedIndex();

        this.verificarComboBoxValida(posicaoSelecionada);

        TijoloBO tijoloBO = new TijoloBO();
        this.tijolos = tijoloBO.buscarTodos();

        Tijolo tijoloSelecionado = tijolos.get(posicaoSelecionada - 1);

        producao.setData(formatador.parse(txtDataProducao.getText()));
        String tipoTijo = (String) cmbTijolo.getSelectedItem();

        String strQuantidade = txtQuantidadeMilheiro.getText().trim();
        if (strQuantidade.isEmpty()) {
            throw new CampoObrigatorioException();
        }
        int quantidade = Integer.parseInt(strQuantidade);
        this.valorVenda = quantidade * tijoloSelecionado.getValorVenda();

        this.lblValorDeVenda.setText(preco.format(valorVenda));
        this.calculaEstimativaDeLucro();

        producao.setQuantidade(quantidade);
        producao.setTipoTijolo(tipoTijo);
    }

    private void calculaEstimativaDeLucro() {
        this.lucroEstimado = this.valorVenda - this.somaGastoTotal;
        this.lblSomaLucroEstimado.setText(this.preco.format(lucroEstimado));
    }

    private void verificacaoComUsuarioParaSalvarProducao() throws SQLException {
        int resposta;
        String mensagem = "Os dados da produção estão corretos?";
        String titulo = "Cadastro de Produção";
        resposta = JOptionPane.showConfirmDialog(this,
                mensagem, titulo, JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {

            this.producao.setGastoTotal(somaGastoTotal);
            this.producao.setValorDeVenda(valorVenda);
            this.producao.setLucroEstimado(lucroEstimado);
            ProducaoBO producaoBO = new ProducaoBO();
            producaoBO.inserir(producao, tarefaFuncionarios);
            this.limpaCamposTela();
            this.tarefaFuncionarios.clear();
            this.carregarTabelaTarefasFuncionario();

            String mensagemSuceso = "Produção cadastrada com sucesso!";
            JOptionPane.showMessageDialog(this,
                    mensagemSuceso, titulo,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void salvarProducao() {
        try {
            this.verificarGastoTotal();
            this.verificaCamposObrigatorios();
            this.verificacaoComUsuarioParaSalvarProducao();
        } catch (SistemaControleProducaoException e) {
            String mensagem = "Erro ao realizar operação:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao administrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + ex.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } catch (Exception e) {
            String mensagem = "Erro inesperado! Informe a mensagem de erro ao aFdministrador do sistema.";
            mensagem += "\nMensagem de erro:\n" + e.getMessage();
            JOptionPane.showMessageDialog(this, mensagem, "Cadastro de produção", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void verificarGastoTotal() {
        if (lblSomaGastoTotal.getText().equals("R$ 0")) {
            throw new GastoTotalInvalidoExcpetion();
        }
    }

    private void recuperarCamposMateriaPrima() {
        this.verificaCampoVenda();

        String strGastoBarro = txtGastoBarro.getText().trim();
        String strGastoLenha = txtGastoLenha.getText().trim();
        strGastoBarro = strGastoBarro.replace(",", ".");
        strGastoLenha = strGastoLenha.replace(",", ".");

        if ((strGastoBarro.isEmpty() || strGastoLenha.isEmpty())) {
            throw new CampoObrigatorioException();
        }

        double gastoBarro = Double.parseDouble(strGastoBarro);
        double gastoLenha = Double.parseDouble(strGastoLenha);

        if (gastoBarro <= 0 || gastoLenha <= 0) {
            throw new GastoNegativoMateriaPrimaException();
        }

        this.somaMateriaPrima = gastoBarro + gastoLenha;
        this.setGastoTotal();

        this.calculaEstimativaDeLucro();

        producao.setGastoBarro(gastoBarro);
        producao.setGastoLenha(gastoLenha);
        //   this.flagMateriaPrima = true;
    }

    private void limpaCamposTela() {
        txtDataProducao.setText("");
        txtGastoBarro.setText("");
        txtGastoLenha.setText("");
        txtGastosExtras.setText("");
        txtLeituraFinalAgua.setText("");
        txtLeituraFinalEletricidade.setText("");
        txtLeituraIncialEletricidade.setText("");
        txtLeituraInicialAgua.setText("");
        txtQuantidadeMilheiro.setText("");
        txtValorDiaria.setText("");
        lblSomaGastoTotal.setText("R$ 0");
        lblSomaLucroEstimado.setText("R$ 0");
        lblValorDeVenda.setText("R$ 0");
        lblValorAgua.setText("R$ 0");
        lblValorEletricidade.setText("R$ 0");
        cmbFuncionario.setSelectedIndex(0);
        cmbTarefa.setSelectedIndex(0);
        cmbTijolo.setSelectedIndex(0);
        this.somaGastoTotal = 0;
        this.somaGastosExtras = 0;
        this.somaMateriaPrima = 0;
        this.valorVenda = 0;
        this.lucroEstimado = 0;
        this.somaGastosFuncionarios = 0;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarGastoMateriaPrima;
    private javax.swing.JButton btnAdicionarGastosExtras;
    private javax.swing.JButton btnAdicionarItemTabela;
    private javax.swing.JButton btnAdicionarProducao;
    private javax.swing.JButton btnExcluirItemFuncionario;
    private javax.swing.JButton btnSalvarProducao;
    private javax.swing.JComboBox cmbFuncionario;
    private javax.swing.JComboBox cmbTarefa;
    private javax.swing.JComboBox cmbTijolo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDataFinal;
    private javax.swing.JLabel lblGasto;
    private javax.swing.JLabel lblLeituraFinal;
    private javax.swing.JLabel lblOutros;
    private javax.swing.JLabel lblSomaGastoTotal;
    private javax.swing.JLabel lblSomaLucroEstimado;
    private javax.swing.JLabel lblTijoloProduzido;
    private javax.swing.JLabel lblValorAgua;
    private javax.swing.JLabel lblValorDeVenda;
    private javax.swing.JLabel lblValorEletricidade;
    private javax.swing.JLabel lblValorOutros;
    private javax.swing.JLabel lbllLeituraInicial;
    private javax.swing.JPanel pnlGastoTotal;
    private javax.swing.JPanel pnlGastosExtras;
    private javax.swing.JPanel pnlLucroEstimado;
    private javax.swing.JPanel pnlMateiraPrima;
    private javax.swing.JPanel pnlProducao;
    private javax.swing.JPanel pnlValorVenda;
    private javax.swing.JTable tblResultado;
    private javax.swing.JFormattedTextField txtDataProducao;
    private javax.swing.JFormattedTextField txtGastoBarro;
    private javax.swing.JFormattedTextField txtGastoLenha;
    private javax.swing.JFormattedTextField txtGastosExtras;
    private javax.swing.JFormattedTextField txtLeituraFinalAgua;
    private javax.swing.JFormattedTextField txtLeituraFinalEletricidade;
    private javax.swing.JFormattedTextField txtLeituraIncialEletricidade;
    private javax.swing.JFormattedTextField txtLeituraInicialAgua;
    private javax.swing.JFormattedTextField txtQuantidadeMilheiro;
    private javax.swing.JFormattedTextField txtValorDiaria;
    // End of variables declaration//GEN-END:variables
private class ModeloTabelaFornecedor extends AbstractTableModel {

        @Override
        public String getColumnName(int coluna) {
            if (coluna == 0) {
                return "Tarefa";
            } else if (coluna == 1) {
                return "Funcionário";
            } else {
                return "Valor Diária";
            }
        }

        @Override
        public int getRowCount() {
            return tarefaFuncionarios.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            FuncionarioTarefaProducao tarefaFuncionario = tarefaFuncionarios.get(rowIndex);

            if (columnIndex == 0) {
                return tarefaFuncionario.getNomeTarefa();
            } else if (columnIndex == 1) {
                return tarefaFuncionario.getNomeFuncionario();
            } else {
                return tarefaFuncionario.getValorDiaria();
            }
        }

    }

}
