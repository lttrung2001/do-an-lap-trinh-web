package quanlycuahang.controller.customer;

import java.util.Date;




import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import quanlycuahang.dao.customer.ClientAccountDAO;
import quanlycuahang.entity.Client;
import quanlycuahang.entity.ClientAccount;

@Controller
public class RegisterController {
	@Autowired
	private ClientAccountDAO clientAccountDAO;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@SuppressWarnings("removal")
	private ClientAccount correctInfomation(ClientAccount account) {
		account.setEmail(account.getEmail().toLowerCase());
		Client info = account.getClientInfo();
		info.setLastName(info.getLastName().toUpperCase());
		info.setFirstName(info.getFirstName().toUpperCase());
		info.setAddress(info.getAddress().toUpperCase());
		account.setClientInfo(info);
		account.setCode(new Integer(new Random().nextInt(100000, 999999)).toString()); 
		return account;
	}
	
	@RequestMapping(value = "register")
	public String register(ModelMap model) {
		model.addAttribute("account", new ClientAccount());
		return "customer/register";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ModelMap model, @Validated @ModelAttribute("account") ClientAccount account,
			BindingResult errors) throws MessagingException {
		if (errors.hasErrors()) {
			model.addAttribute("account", account);
			return "customer/register";
		}
		Client info = account.getClientInfo();
		info.setId(account.getUsername());
		account.setClientInfo(info);
		account.setCreatedDate(new Date());
		account = this.correctInfomation(account);
		int res = clientAccountDAO.checkAccountExists(account);
		// Tr??ng t??n t??i kho???n
		if (res == -1) {
			errors.rejectValue("username", "account", "T??n t??i kho???n ???? t???n t???i!");
		}
		// Tr??ng email
		if (res == -2) {
			errors.rejectValue("email", "account", "Email ???? t???n t???i!");
		}
		if (account.getClientInfo().getPhoneNumber().length() < 10 && !account.getClientInfo().getPhoneNumber().matches("[0-9]")) {
			errors.rejectValue("clientInfo.phoneNumber", "account", "S??? ??i???n tho???i c?? ????? d??i l?? 10 v?? ch??? ch???a c??c k?? t??? s???!");
		}
		if (errors.hasErrors()) {
			model.addAttribute("account", account);
			return "customer/register";
		}
		else {
			clientAccountDAO.createAccount(account);
			// G???i code v??? email
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("no-reply-email");
			helper.setTo(account.getEmail());
			helper.setSubject("CAB STORE: M?? K??CH HO???T T??I KHO???N: " + account.getUsername());
			helper.setText("M?? c???a b???n l??: "+account.getCode());
			mailSender.send(message);
			account.setCode("");
			model.addAttribute("account", account);
			return "customer/verify-email";
		}
	}
	
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	public String verify(ModelMap model, @RequestParam("username") String username, HttpServletRequest request,
						@ModelAttribute("account") ClientAccount account, BindingResult errors) {
		account.setUsername(username);
		String code = account.getCode().toString();
		System.out.println(code);
		account = clientAccountDAO.getClientAccountByUsername(username);
		if (account.getCode().equals(code)) {
			account.setCode("");
			clientAccountDAO.updateAccount(account);
			return "customer/verify-success";
		}
		else if (code.equals("")) {
			errors.rejectValue("code", "account", "Kh??ng ????? tr???ng m?? code!");
		}
		else {
			errors.rejectValue("code", "account", "M?? code kh??ng ????ng!");
		}
		model.addAttribute("account", account);
		return "customer/verify-email";
	}
}
