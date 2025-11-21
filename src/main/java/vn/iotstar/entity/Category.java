package vn.iotstar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

/**
 * Class Category ánh xạ bảng Categories trong database Sử dụng Lombok để tự
 * động tạo getter, setter, constructor
 */
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "Categories")
@NamedQuery(name = "category.findAll", query = "SELECT c FROM Category c") 
public class Category implements Serializable {

	private static final long serialVersionUID = 1L; 

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "CategoryId", nullable = false, unique = true)
	private int categoryId;

	@Column(name = "CategoryName", nullable = false, columnDefinition = "NVARCHAR(200)")
	private String categoryName;
	
	@Column(name = "CategoryCode", nullable = false, columnDefinition = "NVARCHAR(200)")
	private String categoryCode;
	
	@Column(name = "Images", columnDefinition = "NVARCHAR(1000)")
	private String images;

	@Column(name = "Status")
	private int status;
	
	@ManyToOne
	@JoinColumn(name = "UserName")
	private User user;
	
	@OneToMany(mappedBy = "category")
	// Một Category có nhiều Video
	// mappedBy="category" trỏ đến field category trong class Video
	private List<Video> videos = new ArrayList<>(); 

	//Thêm video vào category. Đồng thời set category của video để giữ quan hệ 2 chiều
	public Video addProduct(Video video) {
		this.videos.add(video); // Thêm vào danh sách videos
		video.setCategory(this); // Đồng bộ 2 chiều
		return video;
	}

	//Xóa video khỏi category Đồng thời set category của video về null để giữ quan hệ 2 chiều
	public Video removeProduct(Video video) {
		this.videos.remove(video); // Xóa khỏi danh sách videos
		video.setCategory(null); // Đồng bộ 2 chiều
		return video;
	}
}
