package zadaci;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Roba;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Zadatak4BrisanjeVrednosti {
    static Dao<Roba,Integer> robaDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");

            robaDao = DaoManager.createDao(connectionSource, Roba.class);

            List<Roba> robe = robaDao.queryForAll();
            for (Roba r : robe)
                System.out.println(r);

            List<Roba> robaZaBrisanje = robaDao.queryForEq(Roba.POLJE_NAZIV, "Voda");
            Roba roba1 = robaZaBrisanje.get(0);
            robaDao.delete(roba1);

            List<Roba> robe2 = robaDao.queryForAll();
            for (Roba r : robe2)
                System.out.println(r);
            


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
