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
		// Trùng tên tài khoản
		if (res == -1) {
			errors.rejectValue("username", "account", "Tên tài khoản đã tồn tại!");
		}
		// Trùng email
		if (res == -2) {
			errors.rejectValue("email", "account", "Email đã tồn tại!");
		}
		if (account.getClientInfo().getPhoneNumber().length() < 10 && !account.getClientInfo().getPhoneNumber().matches("[0-9]")) {
			errors.rejectValue("clientInfo.phoneNumber", "account", "Số điện thoại có độ dài là 10 và chỉ chứa các ký tự số!");
		}
		if (errors.hasErrors()) {
			model.addAttribute("account", account);
			return "customer/register";
		}
		else {
			clientAccountDAO.createAccount(account);
			// Gửi code về email
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("no-reply-email");
			helper.setTo(account.getEmail());
			helper.setSubject("CAB STORE: MÃ KÍCH HOẠT TÀI KHOẢN: " + account.getUsername());
			helper.setText("Mã của bạn là: "+account.getCode());
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
			errors.rejectValue("code", "account", "Không để trống mã code!");
		}
		else {
			errors.rejectValue("code", "account", "Mã code không đúng!");
		}
		model.addAttribute("account", account);
		return "customer/verify-email";
	}
}
