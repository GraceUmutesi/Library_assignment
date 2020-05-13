/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import library.system.model.Checkinout;
import library.system.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Check;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public class CheckinoutDao {
    Session session=null;
    public void saveoperation(Checkinout op){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(op);
        tx.commit();
        session.close();
    }
    public String getOpNUmber(String date){
        session = HibernateUtil.getSessionFactory().openSession();
        Object num = session.createCriteria(Checkinout.class).setProjection(Projections.count("opnumber")).uniqueResult();
        session.close();
        return "OP"+num+date;
    }
    public void increaseordecrease(String bookid, String opcat){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        if(opcat.equals("CHECK IN"))
            session.createQuery("update Book set numberofcopies = numberofcopies+1 where bookid = '"+bookid+"'").executeUpdate();
        else
            session.createQuery("update Book set numberofcopies = numberofcopies-1 where bookid = '"+bookid+"'").executeUpdate();
        tx.commit();
        session.close();
    }
        public String getOperationNumber(String cid,String bid){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx= session.beginTransaction();
        Query c = session.createSQLQuery("select opnumber from checkinout where bookid='"+bid+"' and regno = '"+cid+"' and operationcategory = 'CHECK OUT' and status = 'Have' and rownum=1");
        Object opnumber = c.uniqueResult();
        tx.commit();
        session.close();
        return opnumber.toString();
    }
    
    public void updateOperation(String cid,String bid){
        String opnum = getOperationNumber(cid,bid);
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("update checkinout set status = 'Returned' where opnumber = '"+opnum+"'");
        q.executeUpdate();
        tx.commit();
        session.close();
    }
    
    
     public List<Checkinout> getReportOfData(String colname,String id,String opcat){
      List<Checkinout> data = new ArrayList<>();
       try{ 
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Checkinout where "+colname+" = '"+id+"' and operationcategory = '"+opcat+"'");
        data = q.list();
        tx.commit();
        session.close();
    }catch(HibernateException ex){
           JOptionPane.showMessageDialog(null, ex.getMessage());
    }
        return data;
    }
    public List<Checkinout> getReportOfDataDate(Date date1,Date date2,String opcat){
        List<Checkinout> data = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria c = session.createCriteria(Checkinout.class);
        c.add(Restrictions.between("datetime", date1, date2));
        c.add(Restrictions.eq("operationcategory", opcat));
        data = c.list();
        tx.commit();
        session.close();
        return data;
    }
    public List<Object[]> getReportOfDataCategory(String cat,String opcat){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createSQLQuery("select checkinout.opnumber,checkinout.regno,checkinout.bookid,checkinout.datetime,checkinout.operationcategory,checkinout.status from checkinout  join book  on checkinout.bookid=book.bookid join bookcategory  on book.categoryid=bookcategory.categoryid  where categoryname = '"+cat+"' and operationcategory= '"+opcat+"'");
        List<Object[]> data = q.list();
        tx.commit();
        session.close();
        return data;
    }
    public List<Checkinout> getCheckInOut() {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Criteria crt = session.createCriteria(Checkinout.class);
        List<Checkinout> book = crt.list();
        tx.commit();
        session.close();
        return book;
    }    
   public String getId(String className, String extcol, String col, String newid) {
        String id = null;
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select " + newid + " from " + className + " where " + extcol + "='" + col + "'");
        List<String> list = q.list();
        tx.commit();
        session.close();
        for(String l:list)
            id=l;
        return id;
    }
}
