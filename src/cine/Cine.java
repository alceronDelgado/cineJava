/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cine;

import javax.swing.*;

/**
 *
 * @author lalej
 */
public class Cine {

    //Función para guardar los asientos en la matriz.
    public static String[][] definirAsientos() {

        String asientos[][] = new String[11][11];
        //StringBuilder salaCine = new StringBuilder();
        String salaCine[][] = new String[11][11];
        String asiento = "(O)";

        //Agrego los asientos a la sala de cine
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[0].length; j++) {
                salaCine[i][j] = asiento;
            }
        }

        return salaCine;

    }

    //Función para transformar la matriz en un string y vizualizarlo en el JOptionPane.
    public static String mostrarSala(String[][] salaCine) {
        StringBuilder sc = new StringBuilder();

        // Función para insertar la primera sala de cine.
        for (int i = 0; i < salaCine.length; i++) {
            for (int j = 0; j < salaCine[0].length; j++) {
                sc.append(salaCine[i][j]);
            }
            sc.append("\n");
        }

        //Transformo el stringbuilder a string.
        return sc.toString();
    }

    //Función para asignar el asiento.
    public static String[][] asignarAsiento(String[] coordenadas, String salaCine[][]) {
        String asientoReservado = "(+)";
        int fila = Integer.parseInt(coordenadas[0]);
        int columna = Integer.parseInt(coordenadas[1]);

        //Con las coordenadas vemos si hay un asiento vacio, si esta vacio lo asignamos como asiento reservado.
        if (salaCine[fila][columna].equals("(O)")) {
            //Asigno el asiento
            salaCine[fila][columna] = asientoReservado;

        } else {

            JOptionPane.showMessageDialog(null, "Asiento ocupado.");

        }

        return salaCine;

    }

    //Función calcular total
    public static Integer totalPagar(Integer valorBoleta, Integer asientos, Integer nroCuotas, String optionSelect) {
        Integer totalAPagar = 0;

        if (optionSelect.equals("Tarjeta")) {

            while (true) {
                nroCuotas = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite la cantidad de cuotas"));
                if (nroCuotas <= 0) {

                    JOptionPane.showMessageDialog(null, "Nro de cuotas no puede ser igual a 0.");
                } else {
                    totalAPagar = (asientos * valorBoleta) / nroCuotas;
                    break;
                }

            }

        }

        if (optionSelect.equals("Efectivo")) {
            totalAPagar = (valorBoleta * asientos);
        }

        return totalAPagar;
    }

    public static void main(String[] args) {

        String salaCineActual[][] = definirAsientos();
        String sc = mostrarSala(salaCineActual);
        Integer i = 0;
        Integer valorBoleta = 12000;
        String[] opciones = {"Agregar Más asientos", "Elegir método pago"};
        String cantidadAsientos;
        Integer asientos = 0;
        Integer nroCuotas = 0;
        String optionSelect = "";

        Integer intentos = 0;

        //Uso el acomulador para determinar la cantidad de asientos que elige.
        while (true) {

            cantidadAsientos = JOptionPane.showInputDialog(null, "Bienvenido a Cinemax. \n Digite la cantidad de asientos que desea reservar");

            if (cantidadAsientos == null) {
                JOptionPane.showMessageDialog(null, "Sales del programa");
                break;

            }

            try {

                //Transformo los asientos a interos.
                asientos = Integer.parseInt(cantidadAsientos);

                while (i < asientos) {

                    String coorAsientos = JOptionPane.showInputDialog(
                            null,
                            """
                        Fila y columna donde deseas asignar el asiento #: 
                        """ + (i + 1) + "\n Ejemplo: (5:3) \n" + sc + "\n"
                    );

                    //Asigno lo asientos en base a las coordenadas
                    String[] coordenadas = coorAsientos.split(":");

                    //Asigno lo asientos en base a las coordenadas.
                    salaCineActual = asignarAsiento(coordenadas, salaCineActual);
                    i++;
                    //Guardo la cantidad de asientos para poder calcular el total a pagar.
                    cantidadAsientos += 1;
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                break;
            }

            int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Asiento asignado: \n" + mostrarSala(salaCineActual) + "\n",
                    "Cine",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (opcion == 0) {
                i = 0;

            } else {
                break;
            }

            //SECCION DE OPCIONES
            //Combobox
            String[] opcionesComboBox = {"Efectivo", "Tarjeta"};
            JComboBox combo1 = new JComboBox<String>(opcionesComboBox);

            //Método de pago.
            while (intentos < 1) {

                Integer metodoPago = JOptionPane.showConfirmDialog(
                        null,
                        combo1,
                        "Selecciona una opción",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
                );

                //Si el usuario presiona el botón cancelar o el botón de cerrar ventana.
                if (metodoPago == JOptionPane.CANCEL_OPTION || metodoPago == JOptionPane.CLOSED_OPTION) {

                    JOptionPane.showMessageDialog(null, "No se seleccionó ninguna opción.");
                    intentos++;
                    continue;

                } else if (metodoPago == JOptionPane.OK_OPTION) {
                    //Capturar la opción seleccionada.
                    optionSelect = (String) combo1.getSelectedItem();
                    break;
                }

                if (intentos <= 1) {
                    break;
                }

            }

        }

        if (asientos == 0) {

            JOptionPane.showMessageDialog(null, "Exit.");

        } else {

            Integer totalPagar = totalPagar(valorBoleta, asientos, nroCuotas, optionSelect);

            JOptionPane.showMessageDialog(null, """
                                                Valor Boleta: $""" + valorBoleta + "\n" + """
                                                                                 Cantidad de asientos asignados: """ + asientos + "\n" + """
                                                                                                                                      Total a pagar: $""" + totalPagar + "\n" + """
                                                                                                                                                                     ¡Disfruta tu película!""");

        }
    }
}
