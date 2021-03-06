package quanlycuahang.controller.customer;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import quanlycuahang.dao.customer.ClientAccountDAO;
import quanlycuahang.entity.ClientAccount;

@Controller
public class CheckoutController {
	@Autowired
	private ClientAccountDAO clientAccountDAO;
	
	@RequestMapping(value = "checkout")
	public String checkout(HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession();
		ClientAccount accountInSession = (ClientAccount) session.getAttribute("account");
		if (accountInSession == null) return "customer/cart";
		ClientAccount account = clientAccountDAO.getClientAccountByUsername(accountInSession.getUsername());
		model.addAttribute("account", account);
		return "customer/cart";
	}
}
