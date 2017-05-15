package br.com.vaichover.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class DateUtils {

    /**
     * Transforma uma string em data
     * <p>
     *     Metodo usado para fazer o parse de uma string em Date
     * </p>
     *
     * @param       dataStr valor a ser transformado em Date
     * @return      Date    data parseada
     * @see         Date
     */
    public static Date parseStringToDate(String dataStr){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return convertedDate;
    }

    /**
     * Transforma um Date em string
     * <p>
     *     Metodo usado para formatar um valor Date em uma string no formato dd/MM/yyyy
     * </p>
     *
     * @param       date    valor a ser transformado em string
     * @return      String  data formatado em dd/MM/yyyy
     * @see         String
     */
    public static String parseDateToString(Date date){

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    /**
     * Recupera apenas o ano atual
     * <p>
     *     Metodo usado para recuperar apenas o ano
     * </p>
     *
     * @param       date    valor para recuperar o ano
     * @return      String  ano recuperado
     * @see         String
     */
    public static String getYearFromDate(Date date){

        Format formatter = new SimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    /**
     * Transforma Date em string
     * <p>
     *     Metodo usado para fazer o parse de um Date em String e prepara o valor
     *     para uma consulta no formato yyyy-MM-dd
     * </p>
     *
     * @param       date    valor para gerar a query string
     * @return      String  query string formatada
     * @see         String
     */
    public static String parseDateToQueryString(Date date){

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
