package team3;

import team3.entities.HasId;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static team3.Application.scanner;

public class Utilities {
    public static <T extends HasId> T askAndVerifyList(String question, List<T> list, String elementName, boolean showList) {
        if (elementName == null) {
            elementName = "Element";
        }
        if (list.isEmpty()) {
            System.err.println("Nessun/a " + elementName + " presente");
            return null;
        }
        if (showList) {
            list.forEach(System.out::println);
        }
        T element = null;
        while (true) {
            System.out.println(question);

            try {
                long idElement = Long.parseLong(Application.scanner.nextLine());
                element = list.stream().filter(e -> e.getId() == idElement).findFirst().orElse(null);
                if (element == null) {
                    System.err.println(elementName + " non trovato");
                } else {
                    return element;
                }
            } catch (NumberFormatException e) {
                System.err.println("Inserisci un numero valido");
            }
        }
    }

    public static <T extends Enum<T>> T askAndVerifyEnum(String question, Class<T> enumClass) {
        while (true) {
            System.out.println(question);
            List<Integer> integers = new ArrayList<>();
            int i = 1;
            for (T t : enumClass.getEnumConstants()) {
                integers.add(i);
                System.out.println(i + ". " + t.name());
                i++;
            }
            try {
                int output = Integer.parseInt(Application.scanner.nextLine());
                if (integers.contains(output)) {
                    return enumClass.getEnumConstants()[output - 1];
                } else {
                    System.err.println("Inserisci un numero valido");
                }
            } catch (NumberFormatException e) {
                System.err.println("Inserisci un numero valido");
            }
        }
    }

    public static <T extends Enum<T>> T askAndVerifyEnum(String question, Class<T> enumClass, int lastEmentsToRemove) {
        while (true) {
            System.out.println(question);
            List<Integer> integers = new ArrayList<>();

            T[] enumElements = enumClass.getEnumConstants();
            for (int j = 0; j < enumElements.length - lastEmentsToRemove; j++) {
                System.out.println(j + 1 + ". " + enumElements[j].name());
                integers.add(j + 1);
            }
            try {
                int output = Integer.parseInt(Application.scanner.nextLine());
                if (integers.contains(output)) {
                    return enumClass.getEnumConstants()[output - 1];
                } else {
                    System.err.println("Inserisci un numero valido");
                }
            } catch (NumberFormatException e) {
                System.err.println("Inserisci un numero valido");
            }
        }
    }


    public static int askAndVerifyInt(String question) {
        while (true) {
            System.out.println(question);
            try {
                return Integer.parseInt(Application.scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Inserisci un numero valido");
            }
        }
    }

    public static int askAndVerifyIntInList(String question, List<Integer> list) {
        while (true) {
            System.out.println(question);
            try {
                int output = Integer.parseInt(Application.scanner.nextLine());
                if (list.contains(output)) {
                    return output;
                } else {
                    System.err.println("Inserisci un numero valido");
                }
            } catch (NumberFormatException e) {
                System.err.println("Inserisci un numero valido");
            }
        }
    }

    public static String askAndVerifyString(String question) {
        while (true) {
            System.out.println(question);

            String string = Application.scanner.nextLine();

            if (string.isEmpty()) {
                System.err.println("Inserisci un valore valido");
            } else {
                return string;
            }
        }
    }

    public static String askAndVerifyString(String question, int length) {
        while (true) {
            System.out.println(question);

            String string = Application.scanner.nextLine();

            if (string.isEmpty() || string.length() != length) {
                System.err.println("Inserisci un valore valido");
            } else {
                return string;
            }
        }
    }

    public static LocalDate askAndVerifyDate(String question) {
        while (true) {
            System.out.println(question);

            String string = scanner.nextLine();

            if (string.isEmpty()) {
                System.err.println("Inserisci una data");
            } else {
                try {
                    return LocalDate.parse(string);
                } catch (DateTimeParseException e) {
                    System.err.println("Data non valida, riprova");
                    System.out.println();
                }
            }
        }
    }

    public static void pressEnterToContinue() {
        System.out.println();
        System.out.println("Premi INVIO per continuare...");
        scanner.reset();
        scanner.nextLine();
        scanner.reset();

    }
}
