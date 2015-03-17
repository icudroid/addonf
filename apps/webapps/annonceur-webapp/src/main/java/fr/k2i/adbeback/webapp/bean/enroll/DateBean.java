package fr.k2i.adbeback.webapp.bean.enroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateBean implements Serializable {

    private Logger logger = LoggerFactory.getLogger(DateBean.class);

    private Integer day;
    private Integer month;
    private Integer year;

    public DateBean() {
        super();
    }

    public DateBean(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
        }
    }

    public DateBean(LocalDate dateMidnight) {
        if (dateMidnight != null) {
            day = dateMidnight.getDayOfMonth();
            month = dateMidnight.getMonthValue();
            year = dateMidnight.getYear();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((day == null) ? 0 : day.hashCode());
        result = prime * result + ((month == null) ? 0 : month.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateBean other = (DateBean) obj;
        if (day == null) {
            if (other.day != null)
                return false;
        } else if (!day.equals(other.day))
            return false;
        if (month == null) {
            if (other.month != null)
                return false;
        } else if (!month.equals(other.month))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DateBean [day=" + day + ", month=" + month + ", year=" + year + "]";
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public Date getDate(){
        return toDate();
    }

    public Date toDate() {
        if (day == null || month == null || year == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTimeInMillis(0); // EPOC
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        
        Date res = null;
        try{
            res = calendar.getTime();
        }catch (Exception e) {
            logger.info("Date non valide"); 
        }
        
        return res;
    }

    public LocalDate toLocalDate() {
        if (day == null || month == null || year == null) {
            return null;
        }
        return LocalDate.of(year, month, day);
    }

    public boolean isValid() {
        if (day == null || month == null || year == null) {
            return false;
        }
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException ex) {
            logger.debug("isValid",ex);
            return false;
        }
        return true;
    }

    public String toUrlString() {
        if (isValid()) {
            return day + "/" + month + "/" + year;
        }
        else return null;
    }
}
