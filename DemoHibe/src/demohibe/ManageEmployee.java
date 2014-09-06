/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demohibe;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author anilsharma
 */
public class ManageEmployee {
    private static SessionFactory factory = null;
    
    public static void main(String[] args){
        try{
        factory = new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex){
            System.err.println("Failed to create sessionFactory object:"+ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageEmployee ME = new ManageEmployee();
        Integer empID1 = ME.addEmployee("Anil", "Sharma", 2000);
        Integer empID2 = ME.addEmployee("Kapil", "Sharma", 3000);
        
        ME.listEmployee();
    }
    public Integer addEmployee(String fname, String lname, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            System.out.println("Added new employee:"+employeeID);
            tx.commit();
        }catch(HibernateException he){
            if(tx!=null) tx.rollback();
            he.printStackTrace();
        }finally{
            session.close();
        }
        return employeeID;
    }
    
    public void listEmployee() {
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for(Iterator iterator = employees.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name:"+ employee.getFirstName());
                System.out.print("  Last Name:"+ employee.getLastName());
                System.out.println("  Salary:"+ employee.getSalary());
            }
            tx.commit();
        }catch(HibernateException he){
            if(tx!=null) tx.rollback();
            he.printStackTrace();
        }finally{
            session.close();
        }
    }
}
