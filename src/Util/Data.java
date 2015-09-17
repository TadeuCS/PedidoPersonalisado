package Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public class Data {

    private String data;
    private int dia;
    private int mes;
    private int ano;
    SimpleDateFormat format;

    public static String getIdade(Date dataNascimento) {
        GregorianCalendar hj = new GregorianCalendar();
        GregorianCalendar nascimento = new GregorianCalendar();
        nascimento.setTime(dataNascimento);
        int idade = hj.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
        return idade + "";
    }

    public static Date getDataByTexto(String data, String formato) {
        try {
            Date date = null;
            DateFormat formatter = new SimpleDateFormat(formato);
            date = (java.util.Date) formatter.parse(data);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getData(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        Date data = new Date();
        return sdf.format(data);
    }

    public static String getDataByDate(Date data, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(data);
    }

    public String completaData(String texto, String formato) {
        if (texto.contains("  /  /    ")) {
            texto = getData(formato);
        } else {
            if (texto.endsWith("/  /    ")) {
                String dataAtual = getData(formato);
                texto = dataAtual.replace(dataAtual.substring(0, 2), texto.substring(0, 2));
            } else {
                if (texto.endsWith("/    ")) {
                    String dataAtual = getData(formato);
                    texto = texto.replaceAll("    ", dataAtual.substring(6, 10));
                } else {
                    if ("  ".equals(texto.substring(8, 10))) {
                        String dataAtual = getData(formato);
                        texto = texto.replaceAll(texto.substring(6, 8) + "  ", dataAtual.substring(6, 8) + texto.substring(6, 8));
                    }
                }
            }
        }
        return validaData(texto);
    }

    public String validaData(String dataTemp) {

        int mesTemp;
        int diaTemp;
        data = dataTemp.replace("/", "");
        dia = Integer.parseInt(data.trim().substring(0, 2));
        mes = Integer.parseInt(data.trim().substring(2, 4));
        ano = Integer.parseInt(data.trim().substring(4, 8));
        mesTemp = checaMes(mes);
        if (mesTemp != -1) {
            mes = mesTemp;
            diaTemp = checaDia(dia);
            if (diaTemp != -1) {
                dia = diaTemp;
            } else {
                JOptionPane.showMessageDialog(null, "Data Inválida");
                dataTemp = null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Inválida!");
            dataTemp = null;
        }
        return dataTemp;
    }

    public int checaMes(int mesTemp) {

        if (mesTemp > 0 && mesTemp <= 12) {
            return mesTemp;
        } else {
            return -1;
        }
    }

    public int checaDia(int diaTemp) {
        int ultimoDiaMes[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (diaTemp > 0 && diaTemp <= ultimoDiaMes[mes]) {
            return diaTemp;
        } else if ((mes == 2 && diaTemp == 29) && (ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) {
            return diaTemp;
        } else {
            return -1;
        }
    }

    public Date getMenorData(Date data) {
        try {
            format = new SimpleDateFormat("dd/MM/yyyy");
            Date agora = new Date();
            if (agora.before(data)) {
                return agora;
            } else {
                return data;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao comparar as datas!\n" + e.getMessage());
            return null;
        }
    }

    public String addDayOfDate(Date data, int dias) {
        Calendar c = Calendar.getInstance();
        format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            c.setTime(data);
            c.add(Calendar.DATE, +dias);
        } catch (Exception e) {
            System.out.println("Erro ao adicionar " + dias + " a data de valide: " + e.getMessage());
        }
        return format.format(c.getTime());
    }
}
