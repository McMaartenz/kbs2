package com.kbs.warehousemanager.algoritmes;

public abstract class WorstFitAlgoritme {
    static int[] ruimteInDoos = new int[] {10, 10, 10, 10};
    public static int zoekGrootsteRuimte(){

        int doos1 = ruimteInDoos[0];
        int doos2 = ruimteInDoos[1];
        int doos3 = ruimteInDoos[2];
        int doos4 = ruimteInDoos[3];

        if(doos1 > doos2 && doos1 > doos3 && doos1 > doos4 ) {
            return 0;
        }
        if(doos2 > doos1 && doos2 > doos3 && doos2 > doos4 ) {
            return 1;
        }
        if(doos3 > doos1 && doos3 > doos2 && doos3 > doos4 ) {
            return 2;
        } else {
            return 3;
        }
    }
    public static int bepaalDoos(int gewichtProduct) {
        //grootsteDoos is de doos waar nog de meeste ruimte in zit
        int grootsteDoos = zoekGrootsteRuimte();
        int doosnummer = 4;
        if (gewichtProduct <= grootsteDoos) {
            doosnummer = grootsteDoos;
            grootsteDoos -= gewichtProduct;
        }
        return doosnummer;
    }
}
