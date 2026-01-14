package service.auth;

import dao.TechnicalDao;
import model.Technical;

public class TechnicalService {

    public static boolean existTechnicalInDatabase(int id) throws Exception {
        TechnicalDao technicalDao = new TechnicalDao();
        Technical technical = technicalDao.readById(id);
        return technical != null;
    }
}
