package github.com.rexfilius.tea.utils;

import github.com.rexfilius.tea.modules.category.model.Category;
import github.com.rexfilius.tea.modules.category.model.CategoryDto;
import github.com.rexfilius.tea.modules.comment.model.Comment;
import github.com.rexfilius.tea.modules.comment.model.CommentDto;
import github.com.rexfilius.tea.modules.post.model.Post;
import github.com.rexfilius.tea.modules.post.model.PostDto;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;

public abstract class ModelMapper {

    public static PostDto modelToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        if (post.getCategory() != null) {
            postDto.setCategoryId(post.getCategory().getId());
        }
        return postDto;
    }

    public static Post dtoToModel(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }

    public static CategoryDto modelToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    public static Category dtoToModel(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    public static CommentDto modelToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    public static Comment dtoToModel(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
