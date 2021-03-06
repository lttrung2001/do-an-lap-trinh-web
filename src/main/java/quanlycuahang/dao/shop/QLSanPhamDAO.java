package quanlycuahang.dao.shop;

import java.io.File;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import quanlycuahang.bean.UploadFile;
import quanlycuahang.entity.Product;
@Repository
@Transactional
public class QLSanPhamDAO {
	@Autowired
	SessionFactory factory;
	
	@Autowired
	UploadFile baseUploadFile;
	
	public List<Product> getAllProduct() {
		Session session = factory.getCurrentSession();
		String hql ="from Product as pr order by pr.id desc";
		Query query = session.createQuery(hql);
		List<Product> listProduct = query.list();
		return listProduct;
	}
	
	
	
	public List<Product> getTop20Product(){
		Session session = factory.getCurrentSession();
		String hql="FROM Product as pro order by pro.insertDate desc";
		Query query = session.createQuery(hql).setMaxResults(20);
		List<Product> listTop20 =query.list();
		return listTop20;
	}
	
	public Product getProductByID(int id) {
		Session session = factory.getCurrentSession();
		String hql = "from Product where id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		Product list = (Product) query.list().get(0);
		return list;
	}
	
	public Product getProductByName(String name) {
		Session session = factory.getCurrentSession();
		String hql = "from Product where name = :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		Product list = (Product) query.list().get(0);
		return list;
	}
	
	public List<Product> searchProduct(String name) {
		Session session = factory.getCurrentSession();
		String hql = "from Product where name like :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", "%" + name + "%");

		List<Product> list = query.list();
		return list;
	}
	
	public int insertProduct(Product product) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.save(product);
			t.commit();
			session.close();
			return 1;
		} catch (Exception e) {
			t.rollback();
			session.close();
			return 0;
		}
	}
	
	
	public int updateProduct(Product product) {
		System.out.println(product.toString());
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.update(product);
			t.commit();
			session.close();
			return 1;	
		} catch (Exception e) {
			t.rollback();
			session.close();
			return 0;
		}
	}
	
	public int deleteProduct(Product product) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		try {
			session.delete(product);
			t.commit();
			session.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			t.rollback();
			session.close();
			return 0;
		}
	}
	
	public String uploadFile1(MultipartFile file) {
		String fileName = "";
		if (!file.isEmpty()) {
			try {
				fileName = baseUploadFile.setFileName(file);
				String photoPath = baseUploadFile.getBasePath() + File.separator + fileName;
				file.transferTo(new File(photoPath));
				Thread.sleep(2000);
			} catch (IllegalStateException | IOException | InterruptedException e) {
				e.printStackTrace();
				return "0";
			}
		}
		return "/resources/my_image/"+fileName; // "/resources/my_image/ + filename"
	}
	
	public String defaulttName() {
		return baseUploadFile.defaultName();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findAll(Integer offset, Integer maxResults) {
        return factory.getCurrentSession()
                .createQuery("from Product where qttInStock != 0")
                .setFirstResult(offset!=null?offset:0)
                .setMaxResults(maxResults!=null?maxResults:10)
                .list();
    }
	
    public Long count() {
        return (Long)factory.getCurrentSession()
                .createCriteria(Product.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }
}
