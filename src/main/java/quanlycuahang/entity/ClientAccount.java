package quanlycuahang.entity;

import java.util.Date;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "CLIENT_ACCOUNT")
public class ClientAccount {
	@Id
	@Column(name = "USERNAME", nullable = false)
	@NotBlank(message = "Không để trống username")
	@NotEmpty(message = "Không để trống username")
	private String username;
	@Column(name = "PASSWORD", nullable = false)
	@NotBlank(message = "Không để trống password")
	@NotEmpty(message = "Không để trống password")
	private String password;
	@Column(name = "POINT", nullable = false)
	private int point;
	@Column(name = "CREATE_AT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "EMAIL", nullable = false)
	@NotBlank(message = "Không để trống email!")
	@NotEmpty(message = "Không để trống email!")
	@Email(message = "Email không đúng định dạng!")
	private String email;
	@Column(name = "CODE")
	private String code;
	@OneToOne(mappedBy = "clientAccount")
	private Client clientInfo;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customerAcc")
	private List<Bill> bills;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "accountInCart")
	private List<Cart> products;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Client getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(Client clientInfo) {
		this.clientInfo = clientInfo;
	}
	public List<Bill> getBills() {
		return bills;
	}
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	public List<Cart> getProducts() {
		return products;
	}
	public void setProducts(List<Cart> products) {
		this.products = products;
	}
	public ClientAccount(String username, String password, int point, Date createdDate, String email, String code,
			Client clientInfo, List<Bill> bills, List<Cart> products) {
		super();
		this.username = username;
		this.password = password;
		this.point = point;
		this.createdDate = createdDate;
		this.email = email;
		this.code = code;
		this.clientInfo = clientInfo;
		this.bills = bills;
		this.products = products;
	}
	public ClientAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
