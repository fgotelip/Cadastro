package org.example;

import java.time.LocalDate;

public class Data {
    private int dia;
    private int mes;
    private int ano;

    private Data(int dia, int mes, int ano) {
        this.mes = mes;
        this.dia = dia;
        this.ano = ano;
    }

    public static Data parser(String data) throws DataException {
        if(data.matches("^(0?[1-9]|[12][0-9]|30)/(0?[1-9]|(1[0-2]))/(19\\d{2}|20[01]\\d|202[0-4])$")){
            String [] datas = data.split("/");
            return new Data(Integer.parseInt(datas[0]), Integer.parseInt(datas[1]), Integer.parseInt(datas[2]));
        }
        throw new DataException();
    }

    public int getIdade() {
        LocalDate dataAtual = LocalDate.now();
        int dia = dataAtual.getDayOfMonth();
        int mes = dataAtual.getMonthValue();
        int ano = dataAtual.getYear();


        if(this.ano<ano){
            if(this.mes<mes)
                return ano-this.ano;
            else if (this.mes==mes) {
                if(this.dia<=dia)
                    return ano-this.ano;
                else
                    return ano-this.ano-1;
            }
            else
                return ano-this.ano-1;
        }
            return 0;
    }
}
