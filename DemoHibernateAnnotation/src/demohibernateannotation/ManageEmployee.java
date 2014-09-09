/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demohibernateannotation;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 *
 * @author anil
 */
public class ManageEmployee {
    
    private static SessionFactory factory = null;
    private static ServiceRegistry serviceRegistry = null;

    public static void main(String[] args){
       /*
        try{
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            factory = new AnnotationConfiguration().configure().addPackage("demohibernateannotation").addAnnotatedClass(Employee.class).buildSessionFactory(serviceRegistry);
        }catch(Throwable e){
            System.err.println("Failed to create session factory object:"+e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
        
        ManageEmployee me = new ManageEmployee();
        me.addEmployee("Anil", "Sharma", 2000);
        me.addEmployee("Kapil", "Sharma", 3000);
        me.listEmployee();
        */
    }
    
    public Integer addEmployee(String fname, String lname, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            Employee employee = new Employee();
            employee.setFirstName(fname);
            employee.setLastName(lname);
            employee.setSalary(salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        }catch(Exception e){
            if(tx!=null) tx.rollback();
            System.err.println("Exception:"+e.getMessage());
        }finally{
            session.close();
        }
        return employeeID;
    }
    
    public void listEmployee(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for(Iterator iterator = employees.iterator();iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.print("FirstName:"+employee.getFirstName());
                System.out.print("  LastName:"+employee.getLastName());
                System.out.println("  Salary:"+employee.getSalary());
            }
            tx.commit();
        }catch(Exception e){
            if(tx!=null) tx.rollback();
            System.err.println("Exception:"+e.getMessage());
        }finally{
            session.close();
        }
    }
    
}
