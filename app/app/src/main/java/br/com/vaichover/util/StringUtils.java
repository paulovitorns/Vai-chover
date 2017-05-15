package br.com.vaichover.util;

import java.text.DecimalFormat;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class StringUtils {

    /**
     * Substitui um palavra em especifico da string por um valor
     * <p>
     *     Metodo usado para substituir a palavra {var} em uma frase pré criada
     * </p>
     *
     * @param       label   label que contem a palavra {var} a ser substituida
     * @param       value   valor que ira substituir o {var}
     * @return      String  valor formatado e substituido
     * @see         String
     */
    public static String setValue(String label, String value){

        return label.replace("{var}", value);
    }

    /**
     * Formatação monetário
     * <p>
     *     Metodo usado formatar valores monetários
     * </p>
     *
     * @param       currency    valor a ser formatado
     * @return      String      valor formatado
     * @see         String
     */
    public static String doubleToCurrency(Double currency) {
        DecimalFormat dFormat = new DecimalFormat("####,###,##0.00");
        return dFormat.format(currency);
    }

}
