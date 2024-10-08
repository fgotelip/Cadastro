package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PessoaManager extends JFrame {
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<Pessoa> pessoas;

    public PessoaManager() {
        setTitle("Gerenciador de Pessoas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pessoas = new ArrayList<>();

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);

        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento (DD/MM/AAAA):");
        dataNascimentoField = new JTextField(10);

        JLabel cpfLabel = new JLabel("CPF (XXX.XXX.XXX-XX):");
        cpfField = new JTextField(14);

        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        String[] columnNames = {"Nome", "Data de Nascimento", "CPF", "Idade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(dataNascimentoLabel);
        panel.add(dataNascimentoField);
        panel.add(cpfLabel);
        panel.add(cpfField);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPessoa();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarPessoa();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirPessoa();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    preencherCampos(selectedRow);
                }
            }
        });
    }

    private void adicionarPessoa() {
        try {
            String nome = nomeField.getText();
            String dataNascimento = dataNascimentoField.getText();
            String cpf = cpfField.getText();

            Data data = Data.parser(dataNascimento);
            Cpf cpfObj = Cpf.parser(cpf);
            Pessoa pessoa = new Pessoa(nome, data, cpfObj);
            pessoas.add(pessoa);
            tableModel.addRow(new Object[]{nome, dataNascimento, cpf, data.getIdade()});
            clearFields();
        } catch (DataException e) {
            JOptionPane.showMessageDialog(this, "Data inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (CpfException e) {
            JOptionPane.showMessageDialog(this, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPessoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String nome = nomeField.getText();
                String dataNascimento = dataNascimentoField.getText();
                String cpf = cpfField.getText();

                Data data = Data.parser(dataNascimento);
                Cpf cpfObj = Cpf.parser(cpf);

                Pessoa pessoa = new Pessoa(nome, data, cpfObj);
                pessoas.set(selectedRow, pessoa);

                tableModel.setValueAt(nome, selectedRow, 0);
                tableModel.setValueAt(dataNascimento, selectedRow, 1);
                tableModel.setValueAt(cpf, selectedRow, 2);
                tableModel.setValueAt(data.getIdade(), selectedRow, 3);
                clearFields();
            } catch (DataException e) {
                JOptionPane.showMessageDialog(this, "Data inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (CpfException e) {
                JOptionPane.showMessageDialog(this, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para editar.", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirPessoa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            pessoas.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para excluir.", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        nomeField.setText("");
        dataNascimentoField.setText("");
        cpfField.setText("");
    }

    private void preencherCampos(int row) {
        nomeField.setText((String) tableModel.getValueAt(row, 0));
        dataNascimentoField.setText((String) tableModel.getValueAt(row, 1));
        cpfField.setText((String) tableModel.getValueAt(row, 2));
    }
}
