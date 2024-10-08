package org.example;

import java.text.ParseException;

public class Cpf {
    private String cpf;
    private Cpf(String cpf) {
        this.cpf = cpf;
    }

    public static Cpf parser(String cpf) throws CpfException {
        if(cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            cpf = cpf.replaceAll("[^0-9]", "");

            int digito1=0, posicao=0;
            for(int i=10;i>1;i--){
                digito1 += Character.getNumericValue(cpf.charAt(posicao))*i;
                posicao++;
            }
            digito1 = 11 - digito1%11;
            if(digito1>9)
                digito1=0;
            if(Character.getNumericValue(cpf.charAt(9))!=digito1)
                throw new CpfException();

            int digito2=0;
            posicao=0;
            for(int i=11;i>1;i--){
                digito2 += Character.getNumericValue(cpf.charAt(posicao))*i;
                posicao++;
            }
            digito2 = 11 - digito2%11;
            if(digito2>9)
                digito2=0;
            if(Character.getNumericValue(cpf.charAt(10))!=digito2)
                throw new CpfException();

            return new Cpf(cpf);
        }
        throw new CpfException();
    }
}
