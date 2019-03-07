package br.com.pescadosdobrasil.pescadosdobrasil;

import org.apache.commons.lang3.StringUtils;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public static String formatarData(String data) {

        SimpleDateFormat entrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat saida = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date dataEntrada = entrada.parse(data);

            return saida.format(dataEntrada);
        }
        catch (Exception e) {

            return "erro";
        }
    }

    static public String getWaterType(int type) {

        if (type == 1) {

            return "Doce";
        }
        else if (type == 2) {

            return "Salobra";
        }
        else {

            return "Salgada";
        }
    }

    static public String getPrice(int value, int unit) {

        float decimal = (float)value/100f;

        return "R$" + formatDecimal(decimal) + (unit == 1 ? " Kg" : " Un");
    }

    private static String formatDecimal(float number) {

        float epsilon = 0.004f;

        if (Math.abs(Math.round(number) - number) < epsilon) {

            return String.format("%10.0f", number).trim();
        }
        else {

            return String.format("%10.2f", number).trim();
        }
    }

    static public String getInspectionPaper(int type) {

        if (type == 1) {

            return "Não";
        }
        else if (type == 2) {

            return "Selo municipal";
        }
        else if (type == 3) {

            return "Selo estadual";
        }
        else {

            return "Selo federal";
        }
    }

    static public String getDeliveryType(int type) {

        if (type == 1) {

            return "Não";
        }
        else {

            return "Sim";
        }
    }

    static public String getStockType(int type) {

        if (type == 1) {

            return "Mais de 1kg";
        }
        else if (type == 2) {

            return "Mais de 10kg";
        }
        else if (type == 2) {

            return "Mais de 50kg";
        }
        else if (type == 2) {

            return "Mais de 100kg";
        }
        else if (type == 2) {

            return "Mais de 500kg";
        }
        else {

            return "Mais de 1000kg";
        }
    }

    static public String getHoldType(int type) {

        if (type == 1) {

            return "Fresco";
        }
        else if (type == 2) {

            return "Congelado";
        }
        else if (type == 3) {

            return "Enlatado";
        }
        else if (type == 2) {

            return "Salgado";
        }
        else {

            return "Defumado";
        }
    }

    static public String getFisheryType(int type, boolean isFolder) {

        String result;

        if (type == 1) {

            result = "Camarão";
        }
        else if (type == 2) {

            result = "Caranguejo";
        }
        else if (type == 3) {

            result = "Lagosta";
        }
        else if (type == 4) {

            result = "Lula";
        }
        else if (type == 5) {

            result = "Mexilhão";
        }
        else if (type == 6) {

            result = "Ostra";
        }
        else if (type == 7) {

            result = "Peixe";
        }
        else if (type == 8) {

            result = "Polvo";
        }
        else {

            result = "Outros";
        }

        if (isFolder) {

            return StringUtils.stripAccents(result.toLowerCase());
        }
        else {

            return result;
        }
    }
}
