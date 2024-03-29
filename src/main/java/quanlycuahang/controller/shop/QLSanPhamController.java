package quanlycuahang.controller.shop;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import quanlycuahang.entity.ProductType;
import quanlycuahang.entity.Cart;
import quanlycuahang.entity.Product;
import quanlycuahang.dao.shop.QLSanPhamDAO;
import quanlycuahang.dao.shop.BillDetailDAO;
import quanlycuahang.dao.shop.CartDAO;

@Controller
@RequestMapping("admin/product/")
public class QLSanPhamController {
	@Autowired
	QLSanPhamDAO sanPhamDAO;

	@Autowired
	BillDetailDAO billDetailDAO;

	@Autowired
	CartDAO cartDAO;

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, @ModelAttribute("product") Product product) {
		List<Product> products = sanPhamDAO.getAllProduct();
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("products", products);

		return "shop/QL_SanPham";
	}
	
	@RequestMapping(value = "index1", method = RequestMethod.POST)
	public String index1(ModelMap model, @ModelAttribute("product") Product product) {
		List<Product> products = sanPhamDAO.getAllProduct();
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("products", products);

		return "shop/QL_SanPham";
	}
	@Transactional
	@ModelAttribute("productTypeSelect")
	public List<ProductType> getProductType() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ProductType";
		Query query = session.createQuery(hql);
		List<ProductType> list = query.list();
		return list;
	}

	@RequestMapping(value = "index1", params = "btnAdd", method = RequestMethod.POST)
	public String addProduct(ModelMap model,@Validated @ModelAttribute("product") Product product,
			@RequestParam("file") MultipartFile file,BindingResult errors) {
		/*
		 * System.out.println(product.getName());
		 * System.out.println(product.getName().isBlank());
		 * System.out.println(product.getName().isEmpty());
		 * System.out.println(product.getName().equals(""));
		 * 
		 * System.out.println("chay den day r" + errors.hasErrors());
		 * System.out.println(product.getName().trim().isBlank());
		 * System.out.println(product.getName().trim().isEmpty());
		 */
		if (product.getName().trim().isBlank() || product.getName().trim().isEmpty()) {
			errors.rejectValue("name", "product", "Tên sản phẩm không được để trống!");
			model.addAttribute("products", sanPhamDAO.getAllProduct());
			model.addAttribute("btnStatus", "btnAdd");
			return "shop/QL_SanPham";
		}
		if (product.getQttInStock() == 0) {
			errors.rejectValue("qttInStock", "product", "Số lượng không được bằng 0 hoặc để trống!");
			model.addAttribute("products", sanPhamDAO.getAllProduct());
			model.addAttribute("btnStatus", "btnAdd");
			return "shop/QL_SanPham";
		}
		if (product.getPrice() == 0.0) {
			errors.rejectValue("price", "product", "Giá sản phẩm không được bằng 0.0 hoặc để trống!");
			model.addAttribute("products", sanPhamDAO.getAllProduct());
			model.addAttribute("btnStatus", "btnAdd");
			return "shop/QL_SanPham";
		}
		String fileName = sanPhamDAO.uploadFile1(file);
		if (fileName.equals("/resources/my_image/") || fileName.equals("0")) {
			product.setImage(sanPhamDAO.defaulttName());
		} else {
			product.setImage(fileName);
		}
		product.setInsertDate(new Date());
		model.addAttribute("status", 0);
		int temp = sanPhamDAO.insertProduct(product);
		if (temp != 0) {
			model.addAttribute("message", temp);
			model.addAttribute("btnStatus", "btnAdd");
			model.addAttribute("product", new Product());
		} else {
			model.addAttribute("message", temp);
			model.addAttribute("btnStatus", "btnAdd");
		}
		model.addAttribute("products", sanPhamDAO.getAllProduct());
		return "shop/QL_SanPham";
	}

	@RequestMapping(value = "/index/{id}.htm", params = "linkDelete")
	public String deleteProduct(ModelMap model,

			@ModelAttribute("product") Product product, @PathVariable("id") int id) {
		model.addAttribute("status", 2);
		model.addAttribute("btnStatus", "btnAdd");
		boolean checkProIdInBillDetail;
		checkProIdInBillDetail = billDetailDAO.checkProIDinBillDetail(id);
		if (checkProIdInBillDetail == false) {
			model.addAttribute("message", 2);
			model.addAttribute("products", sanPhamDAO.getAllProduct());
			return "shop/QL_SanPham";
		}
		List<Cart> checkCart = cartDAO.getCartByProID(id);
		if (!checkCart.isEmpty())
			cartDAO.deleteCart(checkCart.get(0));
		int temp = sanPhamDAO.deleteProduct(product);
		if (temp != 0) {

			model.addAttribute("message", temp);

		} else {
			model.addAttribute("message", temp);
		}
		model.addAttribute("products", sanPhamDAO.getAllProduct());
		return "shop/QL_SanPham";
	}

	@RequestMapping(value = "/index/{id}.htm", params = "linkEdit")
	public String editProduct(ModelMap model,

			@ModelAttribute("product") Product product, @PathVariable("id") int id) {

		model.addAttribute("btnStatus", "btnEdit");
		model.addAttribute("product", sanPhamDAO.getProductByID(id));
		model.addAttribute("products", sanPhamDAO.getAllProduct());
		return "shop/QL_SanPham";
	}

	@RequestMapping(value = "index1", params = "btnEdit", method = RequestMethod.POST)
	public String edit_Product(ModelMap model,@Validated @ModelAttribute("product") Product product,
			@RequestParam("file") MultipartFile file,BindingResult errors) {
		Product proTam = sanPhamDAO.getProductByID(product.getId());
		String fileName = sanPhamDAO.uploadFile1(file);
		if (!fileName.equals("/resources/my_image/")) {
			product.setImage(fileName);
		}
		product.setInsertDate(proTam.getInsertDate());
		model.addAttribute("status", 1);
		int temp = sanPhamDAO.updateProduct(product);
		if (temp != 0) {
			model.addAttribute("message", temp);
		} else {

			model.addAttribute("message", temp);
		}
		// model.addAttribute("products", this.getProducts());
		model.addAttribute("products", sanPhamDAO.getAllProduct());
		return "shop/QL_SanPham";
	}

	@RequestMapping(value = "index", params = "btnsearch")
	public String searchPro(HttpServletRequest request, ModelMap model, @ModelAttribute("product") Product product) {
		String name = request.getParameter("searchInput");
		List<Product> list = sanPhamDAO.searchProduct(name);
		model.addAttribute("products", list);
		/* model.addAttribute("products", sanPhamDAO.getAllProduct()); */
		model.addAttribute("btnStatus", "btnAdd");
		return "shop/QL_SanPham";
	}

}
