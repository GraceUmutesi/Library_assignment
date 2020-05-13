/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;

import java.util.List;
import library.system.model.Book;
import library.system.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class BookDao {
        Session session  = null;
        public void saveBook(Book c){
        session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
        session.save(c);
        tx.commit();
        session.close();
    }
    public void updateBook(Book c){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(c);
        tx.commit();
        session.close();
    }public void deleteBook(Book c){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(c);
        tx.commit();
        session.close();
    }
    public List<Book> displayBook(){
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Book> books = (List<Book>) session.createCriteria(Book.class).list();
        tx.commit();
        session.close();
        return books;
    }
    public List<Book> findByBookId(String id){
        
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Book> books = (List<Book>) session.createCriteria(Book.class).add(Restrictions.eq("bookid", id)).list();
        tx.commit();
        session.close();
        return books;
    }
    public String getName(String id){
        session = HibernateUtil.getSessionFactory().openSession();
        Object ob = session.createQuery("select title from Book where bookid = '"+id+"'").uniqueResult();
        session.close();
        return ob.toString();
    }
}
