package vn.iotstar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Users")
@NamedQuery(name = "user.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "UserName", nullable = false, unique = true, columnDefinition = "NVARCHAR(200)")
	private String userName;
	
	@Column(name = "Password", nullable = false, columnDefinition = "NVARCHAR(200)")
	private String password;
	
	@Column(name = "Phone", nullable = false, columnDefinition = "NVARCHAR(20)")
	private String phone;

	@Column(name = "FullName", nullable = false, columnDefinition = "NVARCHAR(200)")
	private String fullName;

	@Column(name = "Email", nullable = false, unique = true, columnDefinition = "NVARCHAR(200)")
	private String email;

	@Column(name = "Admin", nullable = false, columnDefinition = "NVARCHAR(500)")
	private boolean admin;
	
	@Column(name = "Active")
	private int active;
	
	@Column(name = "Images", columnDefinition = "NVARCHAR(500)")
	private String images;

	// Quan hệ: 1 User có nhiều Category
	@OneToMany(mappedBy = "user")
	private List<Category> categories = new ArrayList<>();

	// Đồng bộ 2 chiều
	public Category addCategory(Category category) {
		this.categories.add(category);
		category.setUser(this);
		return category;
	}

	public Category removeCategory(Category category) {
		this.categories.remove(category);
		category.setUser(null);
		return category;
	}
}
