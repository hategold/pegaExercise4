package yt.item4;

import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import yt.item4.bean.Brand;
import yt.item4.bean.Shoes;

public class TestHibernate {

	public static void main(String[] args) {
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Shoes shoes = new Shoes();
		shoes.setShoesName("king").setPrice(5566).setCategory("sport").setSeries("AIR");
		
		
		
		Brand brand = new Brand("ininder3eeeee333");
		brand.setCountry("TWN").setWebsite("www.5566").setBrandId(55);
		shoes.setBrand(brand);
		brand.setShoesGroup(new HashSet<Shoes>());
		brand.addShoes(shoes);
		
		Session session = sessionFactory.openSession();

		@SuppressWarnings("unchecked")
		Query<Brand> query = session.createQuery("From Brand");
		List<Brand> l = query.getResultList();
		for(Brand b :l){
			System.out.println(b);
		}
		session.close();
		
//		session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		brand = (Brand)session.get(Brand.class, 56);
//		System.out.println(brand);
//		session.delete(brand);
//		tx.commit();
//		session.close();
		sessionFactory.close();
		System.out.println("OUT!");
	}
}
