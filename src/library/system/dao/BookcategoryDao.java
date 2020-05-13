/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;

import java.util.List;
import library.system.model.Bookcategory;
import library.system.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public class BookcategoryDao {
    Session session = null;
        public void saveBookcategory(Bookcategory c){
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();
        session.close();
    }
    public void updateBookcategory(Bookcategory c){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(c);
        tx.commit();
        session.close();
    }public void deleteBookcategory(Bookcategory c){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(c);
        tx.commit();
        session.close();
    }
    public List<Bookcategory> displayBookcategory(){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Bookcategory> categories = (List<Bookcategory>) session.createCriteria(Bookcategory.class).list();
        tx.commit();
        session.close();
        return categories;
    }
    public List<Bookcategory> findByCategoryId(String id){
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Bookcategory> categories = (List<Bookcategory>) session.createCriteria(Bookcategory.class).add(Restrictions.eq("categoryid", id)).list();
        tx.commit();
        session.close();
        return categories;
    }
    public String getCategoryname(String id){
        String sql = "select categoryname from Bookcategory where categoryid = '"+id+"'";
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Object name = session.createQuery(sql).uniqueResult();
        tx.commit();
        session.close();
        return name.toString();
    }
    public String getCategoryid(String name){
        String sql = "select categoryid from Bookcategory where categoryname = '"+name+"'";
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Object id = session.createQuery(sql).uniqueResult();
        tx.commit();
        session.close();
        return id.toString();
    }
    public List<String> categorynames(){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> names = session.createCriteria(Bookcategory.class).setProjection(Projections.property("categoryname")).list();
        tx.commit();
        session.close();
        return names;
    }
}
