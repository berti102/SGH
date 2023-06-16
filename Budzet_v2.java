//Aplikacja do zarz¹dzania bud¿etem.

//Funkcje programu
//- prowadzenie dwóch kont - Rozliczenioweiczeniowego i inwestycyjnego
//- dodawanie i wyœwietlanie transakcji
//- przenoszenie œrodków miêdzy kontami
//- zapis i odczyt transakcji z pliku
//- podsumowanie transakcji
//
// 1. pobieranie, wyœwietlanie tekstu
// 2. zmienne (int, float, boolean, tablicowe)
// 3. operacje logiczne, arytmetyczne, przypisania, relacyjne
// 4. warunki, pêtle
// 5. tablice, tablice dynamiczne
// 6. obiektowoœæ, pojêcie klasy


import java.io.*;
import java.text.*;
import java.util.*;


public class Budzet_v2 {
    private static final String FILE_PATH = "budzet.csv";
    
    private ArrayList<Date> dataList;
    private ArrayList<String> nazwaList;
    private ArrayList<Float> kwotaList;
    private ArrayList<String> typkontaList;
    
    public Budzet_v2() {
        dataList = new ArrayList<>();
        nazwaList = new ArrayList<>();
        kwotaList = new ArrayList<>();
        typkontaList = new ArrayList<>();
        
        
        
    }
    
    public static void main (String[] args) {
        Budzet_v2 budzet = new Budzet_v2();
        budzet.uruchom();
}
    
    private void uruchom() {
        wczytajDaneZPliku();
        Scanner podaj = new Scanner(System.in);
        int menu = 1;
        
        while (menu>0) {
        System.out.println("Program do zarz¹dzania bud¿etem osobistym");
        System.out.println();
        System.out.println("Wybierz czynnoœæ");
        System.out.println();
        System.out.println("1. WprowadŸ wp³ywy lub wydatki do konta Rozliczeniowego");
        System.out.println("2. Przenieœ œrodki miêdzy kontami");
        System.out.println("3. Wyœwietl listê transakcji");
        System.out.println("4. Wyœwietl podsumowanie konta Rozliczeniowego");
        System.out.println("5. Wyœwietl podsumowanie konta Oszczednosciowego");
        System.out.println("6. Zapisz do pliku");
        System.out.println("7. Saldo rozliczeniowe");
        System.out.println("8. Saldo oszczednoœciowe");

        System.out.println("0. Zamknij program");
        
        menu = podaj.nextInt();
        
        switch (menu) {
            case 1:
                dodajTransakcje();
                break;
            
            case 2:
                przeniesSrodki(podaj);
                break;
                
            case 3:
                wyswietlListeTransakcji();
                break;
                
            case 4:
                wyswietlPodsumowanieKonta("Rozliczeniowe");
                break;
                
            case 5:
                wyswietlPodsumowanieKonta("Oszczednosciowe");
                break;
                
            case 6:
                zapiszDoPliku();
                System.out.println("Dane zosta³y zapisane");
                break;      
            
            case 7: 
                wyswietlSaldo("Rozliczeniowe");
                break;
            
            case 8:
                wyswietlSaldo("Oszczednosciowe");
                break;
          
        }
        
        }
    }
    
    private void dodajTransakcje() {
        Scanner podaj = new Scanner(System.in);
        String CzyKolejny = "T";
        String typkonta = "Rozliczeniowe";
        
        while (CzyKolejny.equalsIgnoreCase ("T")) {
            System.out.println("WprowadŸ datê transakcji (yyyy-mm-dd): ");
            String dataString = podaj.nextLine();
            
            try {
                DateFormat dateFormat = DateFormat.getDateInstance();
                Date data = dateFormat.parse(dataString);
                dataList.add(data);
            } catch (ParseException e) {
                System.out.println("Nieprawid³owy format daty");
            }
            
            System.out.println("WprowadŸ nazwê transakcji: ");
            String nazwa = podaj.nextLine();
            nazwaList.add(nazwa);
            
            System.out.println("Podaj kwotê: ");
            float kwota = podaj.nextFloat();
            kwotaList.add(kwota);
            
            typkontaList.add(typkonta);
            
            podaj.nextLine();
            System.out.println();
            System.out.print("Czy dodaæ kolejny (T/N)? ");
            CzyKolejny = podaj.nextLine();
            
            if (CzyKolejny.equalsIgnoreCase ("N")) {
                break;
        }
            
            
        }
    }
    
    private void przeniesSrodki (Scanner podaj) {
        podaj.nextLine();
        System.out.println("Wybierz czynnoœæ");
        System.out.println();
        System.out.println("1. Przenieœ œrodki z Rozliczeniowego na Oszczednosciowe");
        System.out.println("2. Przenieœ œrodki z Oszczednosciowego na Rozliczeniowe");
        
        int menu2 = podaj.nextInt();
        
        switch (menu2) {
            case 1:
                przeniesSrodkiMiedzyKontami("Rozliczeniowe", "Oszczednosciowe");
                break;

            case 2:
                przeniesSrodkiMiedzyKontami("Oszczednosciowe", "Rozliczeniowe");
                break;
        }
        
        
        System.out.println();
        
    }
    
    private void przeniesSrodkiMiedzyKontami(String kontoZ, String kontoNa) {
        Scanner podaj = new Scanner(System.in);
        System.out.println("Jak¹ kwotê przenieœæ na konto " + kontoNa + "?");
        Float kwota = podaj.nextFloat();

        float suma = 0.00f;
        for (int i = 0; i < dataList.size(); i++) {
            if (typkontaList.get(i).equals(kontoZ)) {
                suma += kwotaList.get(i);
            }
        }
                
        if (kwota > suma) {
        System.out.println("Niewystarczaj¹ce œrodki");
        }
        
            
        if (kwota < suma) {    
          
                
        String nazwa = "Przelew Wewnêtrzny";
        Date today = Calendar.getInstance().getTime();

        dataList.add(today);
        nazwaList.add(nazwa);
        kwotaList.add(kwota);
        typkontaList.add(kontoNa);

        kwota = -kwota;

        dataList.add(today);
        nazwaList.add(nazwa);
        kwotaList.add(kwota);
        typkontaList.add(kontoZ);
        
        System.out.println("Transakcja wykonana");
        }
    }
    
    private void wyswietlListeTransakcji() {
        System.out.println("Wyœwietlanie");
        System.out.println();
        System.out.println("****************************************************************************************");
        System.out.printf("* %-30s * %-20s * %-10s * %-15s *", "Data", "Nazwa", "Kwota", "Konto");
        //System.out.print("* Data \t\t\t\t");
        //System.out.print("* Nazwa \t\t\t");
        //System.out.print("* Kwota \t");
        //System.out.print("* Konto");
        System.out.println();
        System.out.println("****************************************************************************************");

        for (int i = 0; i < dataList.size(); i++) {
            Date data = dataList.get(i);
            //System.out.print("* " + data + "\t");
            String nazwa = nazwaList.get(i);
            //System.out.print("* " + nazwa + "\t\t");
            Float kwota = kwotaList.get(i);
            //System.out.print("* " + kwota + "\t\t");
            String typkonta = typkontaList.get(i);
            //System.out.println("* " + typkonta);
            System.out.printf("* %-30s * %-20s * %10.2f * %-15s *\n", data, nazwa, kwota, typkonta);
            
        }
        System.out.println();
    }
    
    private void wyswietlPodsumowanieKonta(String typkonta) {
        float suma = 0.00f;
        for (int i = 0; i < dataList.size(); i++) {
            if (typkontaList.get(i).equals(typkonta)) {
                suma += kwotaList.get(i);
            }
        }

        System.out.println("Wyœwietlanie transakcji dla typu " + typkonta);
        System.out.println();
        System.out.println("****************************************************************************************");
        System.out.printf("* %-30s * %-20s * %-10s * %-15s *", "Data", "Nazwa", "Kwota", "Konto");
        //System.out.print("* Data \t\t\t\t");
        //System.out.print("* Nazwa \t\t\t");
        //System.out.print("* Kwota \t");
        //System.out.print("* Konto");
        System.out.println();
        System.out.println("****************************************************************************************");

        for (int i = 0; i < dataList.size(); i++) {
            if (typkontaList.get(i).equals(typkonta)) {
                Date data = dataList.get(i);
                //System.out.print("* " + data + "\t");
                String nazwa = nazwaList.get(i);
                //System.out.print("* " + nazwa + "\t\t");
                Float kwota = kwotaList.get(i);
                //System.out.print("* " + kwota + "\t\t");
                typkonta = typkontaList.get(i);
                //System.out.println("* " + typkontaTransakcji);
                System.out.printf("| %-30s * %-20s * %10.2f * %-15s *\n", data, nazwa, kwota, typkonta);

            }
            
        }
        
        System.out.println();
        System.out.println("****");
        System.out.println("Suma: " + suma);
        System.out.println("****");
        System.out.println();
    }
    
    private void zapiszDoPliku() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < dataList.size(); i++) {
                Date data = dataList.get(i);
                String nazwa = nazwaList.get(i);
                float kwota = kwotaList.get(i);
                String typkonta = typkontaList.get(i);

                DateFormat dateFormat = DateFormat.getDateInstance();
                String dataString = dateFormat.format(data);

                String line = dataString + "," + nazwa + "," + kwota + "," + typkonta;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void wczytajDaneZPliku() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String dataString = parts[0];
                    String nazwa = parts[1];
                    float kwota = Float.parseFloat(parts[2]);
                    String typkonta = parts[3];

                    DateFormat dateFormat = DateFormat.getDateInstance();
                    Date data = dateFormat.parse(dataString);

                    dataList.add(data);
                    nazwaList.add(nazwa);
                    kwotaList.add(kwota);
                    typkontaList.add(typkonta);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    
    private void wyswietlSaldo (String typkonta) {
        float suma = 0.00f;
        for (int i = 0; i < dataList.size(); i++) {
            if (typkontaList.get(i).equals(typkonta)) {
                suma += kwotaList.get(i);
    }
}
        
        System.out.println("****");
        System.out.println("Saldo " + typkonta + ": " + suma);
        System.out.println("****");
        
    }
}
 
// pozycja 7 - wyœwietl dwa salda
// brak mo¿liwoœci przekroczenia salda
