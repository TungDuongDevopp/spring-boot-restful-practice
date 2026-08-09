package com.duong.springdemoresful.model;

import java.time.Instant;
import java.util.List;

import com.duong.springdemoresful.dto.response.LoginResponse;
import com.duong.springdemoresful.helper.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "title không được để trống")
	private String title;

	@NotBlank(message = "content không được để trống")
	@Column(columnDefinition = "MEDIUMTEXT")
	private String content;

	private Instant createdAt;

	private Instant updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

	@ManyToMany
	@JoinTable(name = "post_tag",
	joinColumns = @JoinColumn(name = "post_id"),
	inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

	public Post(String title, String content, User user) {

		this.title = title;
		this.content = content;
		this.user = user;
	}

	@PrePersist
	public void preCreate(){
		createdAt = Instant.now();
		updatedAt = Instant.now();
	
		boolean isLoggedIn = SecurityUtil.getCurrentUsernameLogin().isPresent();
		if(isLoggedIn){
			Long currentId = SecurityUtil.getCurrentIdLogin();
			if(currentId != null && currentId > 0){
				User u = new User();
				u.setId(currentId);
				user = u;
			}
		}
	}

}
