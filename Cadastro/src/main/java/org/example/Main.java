package org.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PessoaManager manager = new PessoaManager();
            manager.setVisible(true);
        });
    }
}
