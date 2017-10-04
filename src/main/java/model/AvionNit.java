package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AvionNit extends Thread {


    public static volatile boolean dozvoljenoSletanje = true;
    private Avion avion;

    public AvionNit(Avion avion){
        this.avion = avion;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public void run(){
        System.out.println("Pocinju provere za avion " + avion.getId());
        try {
            Random random = new Random();
            long vreme = Math.round(random.nextDouble() * 2000);
            this.sleep(vreme);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Avion " + avion.getId() + " je spreman za poletanje i ceka dozvolu za poletanje.");

        do{
            try {
                this.sleep(1000);
                if (dozvoljenoSletanje){
                    dozvoljenoSletanje = false;

                    System.out.println("Avion " + avion.getId() + " izlazi na pistu i polece.");
                    this.sleep(2000);

                    dozvoljenoSletanje = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!dozvoljenoSletanje);
        System.out.println("Avion " + avion.getId() + " je poleteo.");
    }

    static Dao<Avion,Integer> avionDao;
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");

            avionDao = DaoManager.createDao(connectionSource, Avion.class);

            List<AvionNit> listaAviona = new ArrayList<AvionNit>();

            listaAviona.add(new AvionNit(avionDao.queryForId(1)));
            listaAviona.add(new AvionNit(avionDao.queryForId(2)));

            ArrayList<Thread> listaNiti = new ArrayList<>();

            for (AvionNit avionNit : listaAviona){
                Thread t = new Thread(avionNit);
                listaNiti.add(t);
                t.start();
            }

            for (Thread t : listaNiti){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Svi avioni su poleteli");
    }

}


