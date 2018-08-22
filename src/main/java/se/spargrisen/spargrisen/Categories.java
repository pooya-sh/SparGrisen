package se.spargrisen.spargrisen;

public enum Categories {

    BOENDE ("Boende"),
    LIVSMEDEL ("Livsmedel"),
    NÖJEN ("Nöjen"),
    AVGIFTER ("Avgifter"),
    TELEFONI ("Telefoni"),
    RESTURANGBESÖK ("Restaurangbesök"),
    SHOPPING ("Shopping"),
    KLÄDER ("Kläder"),
    HUSDJUR ("Husdjur"),
    FÖRSÄKRINGAR ("Försäkringar"),
    TRANSPORT ("Transport"),
    SPARANDE ("Sparande"),
    LÅN ("Lån"),
    TRÄNING ("Träning"),
    HYGIEN ("Hygien"),
    HUSHÅLLSARTIKLAR ("Hushållsartiklar");

    private final String value;

    private Categories(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString (){
        return value;
    }
}