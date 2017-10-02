package model;

public class AvionNit extends Thread {

    private int sifraAviona;

    public AvionNit(int sifraAviona){
        this.sifraAviona = sifraAviona;
    }

    private void poleti(){
        System.out.println("Avion " + sifraAviona + " je poleteo.");

        try {
            this.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleti(){
        System.out.println("Avion " + sifraAviona + " trazi dozvolu za sletanje.");
        do{
            try {
                // Svake sekudne proveravaj dozvolu za sletanje
                this.sleep(1000);
                if (Aerodrom.dozvoljenoSletanje){
                    Aerodrom.dozvoljenoSletanje = false;

                    // Treba mu jedna sekunda da sleti
                    System.out.println("Avion " + sifraAviona + " slece.");
                    this.sleep(1000);

                    Aerodrom.dozvoljenoSletanje = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!Aerodrom.dozvoljenoSletanje);


        System.out.println("Avion " + sifraAviona + " je sleteo.");
    }

    public void run() {
        poleti();
        sleti();
    }
}


