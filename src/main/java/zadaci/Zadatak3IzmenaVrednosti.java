package zadaci;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class Zadatak3IzmenaVrednosti {
    static Dao<Roba,Integer> robaDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:avionRoba.db");

            robaDao = DaoManager.createDao(connectionSource, Roba.class);

            List<Roba> robaZaIzmenu = robaDao.queryForEq(Roba.POLJE_OPIS, "Plasticna stolica");
            Roba roba1 = robaZaIzmenu.get(0);
            roba1.setOpis("Drvena stolica");
            robaDao.update(roba1);

            List<Roba> robe = robaDao.queryForAll();
            for (Roba r : robe)
                System.out.println(r);



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
