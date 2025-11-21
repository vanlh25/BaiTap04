package vn.iotstar.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

/**
 * Class Video ánh xạ bảng Videos trong database Sử dụng Lombok để tự động
 * tạo getter, setter, constructor
 */
@Getter // Lombok tự tạo getter cho tất cả field
@Setter // Lombok tự tạo setter cho tất cả field
@NoArgsConstructor // Lombok tạo constructor không tham số (cần cho JPA)
@AllArgsConstructor // Lombok tạo constructor với tất cả field

@Entity // Đánh dấu đây là Entity JPA
@Table(name = "Videos") // Tên bảng trong database
@NamedQuery(name = "video.findAll", query = "SELECT v FROM Video v")
 // Query sẵn để lấy tất cả Video
public class Video implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id // Khóa chính
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
	@Column(name = "VideooId", nullable = false, unique = true) // Tên cột trong DB
	private int videoId;

	@Column(name = "Title", columnDefinition = "NVARCHAR(200) ")
	// Ánh xạ cột title, kiểu NVARCHAR(200)
	private String title;

	@Column(name = "Poster", columnDefinition = "NVARCHAR(1000)")
	private String poster;
	
	@Column(name = "Views")
	private int views;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Active")
	private int active;
	
	@ManyToOne // Nhiều video thuộc 1 category
	@JoinColumn(name = "CategoryId") // Cột khóa ngoại liên kết với Category
	private Category category;
}
