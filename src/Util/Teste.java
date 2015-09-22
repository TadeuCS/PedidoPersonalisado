/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Tadeu
 */
public class Teste {

    private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        /* CEP - resultado: 81580-200
         format("#####-###", "81580200");
         CPF - resultado 012.345.699-01
         format("###.###.###-##", "01234569905");
         CNPJ - resultado: 01.234.569/9052-34
         format("##.###.###/####-##", "01234569905234");
         */
        System.out.println(Teste.format("###.###.###-##", "10187469601"));
    }

}
