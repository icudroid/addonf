package fr.k2i.adbeback.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import fr.k2i.adbeback.core.business.ad.QBrand;
import fr.k2i.adbeback.core.business.company.Company;
import fr.k2i.adbeback.core.business.company.QCompany;
import fr.k2i.adbeback.core.business.user.*;
import fr.k2i.adbeback.dao.ICompanyDao;
import org.springframework.stereotype.Repository;


@Repository
public class CompanyDao extends GenericDaoJpa<Company, Long> implements ICompanyDao {

    /**
     * Constructor that sets the entity to User.class.
     */
    public CompanyDao() {
        super(Company.class);
    }

    @Override
    public Company findByUser(User user) {
        QCompany company = QCompany.company;

        JPAQuery<Company> query = new JPAQuery(getEntityManager());


        if (user instanceof MediaUser) {
            MediaUser mediaUser = (MediaUser) user;
            QMedia media = company.as(QMedia.class);
            query.from(media).where(media.user.eq(mediaUser));
        }else if (user instanceof BrandUser) {
            BrandUser brandUser = (BrandUser) user;
            QBrand brand = company.as(QBrand.class);
            query.from(brand).where(brand.user.eq(brandUser));
        }else if (user instanceof AgencyUser) {
            AgencyUser agencyUser = (AgencyUser) user;
            QAgency agency = company.as(QAgency.class);
            QAgencyUser qAgencyUser = QAgencyUser.agencyUser;
            query.from(agency).join(agency.users,qAgencyUser).where(qAgencyUser.eq(agencyUser));
        }

        return query.select(company).fetchOne();
    }

}

