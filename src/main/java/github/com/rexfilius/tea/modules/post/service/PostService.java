package github.com.rexfilius.tea.modules.post.service;

import github.com.rexfilius.tea.modules.category.model.Category;
import github.com.rexfilius.tea.modules.category.repository.CategoryRepository;
import github.com.rexfilius.tea.modules.post.model.Post;
import github.com.rexfilius.tea.exception.ResourceNotFoundException;
import github.com.rexfilius.tea.modules.post.model.AllPostResponse;
import github.com.rexfilius.tea.modules.post.model.PostDto;
import github.com.rexfilius.tea.modules.post.repo.PostRepository;
import github.com.rexfilius.tea.utils.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static github.com.rexfilius.tea.utils.ModelMapper.*;

@Service
public class PostService {
        private final PostRepository postRepository;
        private final CategoryRepository categoryRepository;

        public PostService(
                        PostRepository postRepository,
                        CategoryRepository categoryRepository) {
                this.postRepository = postRepository;
                this.categoryRepository = categoryRepository;
        }

        public PostDto createPost(PostDto postDto) {
                Category category = categoryRepository.findById(postDto.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                                                postDto.getCategoryId()));

                Post post = dtoToModel(postDto);
                post.setCategory(category);
                Post savedPost = postRepository.save(post);
                return modelToDto(savedPost);

        }

        public AllPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Post> postPage = postRepository.findAll(pageable);
                Page<PostDto> postDtoPage = postPage.map(ModelMapper::modelToDto);

                AllPostResponse postResponse = new AllPostResponse();
                postResponse.setPosts(postDtoPage.getContent());
                postResponse.setPageNo(postDtoPage.getNumber());
                postResponse.setPageSize(postDtoPage.getSize());
                postResponse.setTotalElements(postDtoPage.getTotalElements());
                postResponse.setTotalPages(postDtoPage.getTotalPages());
                postResponse.setLast(postDtoPage.isLast());
                return postResponse;
        }

        public PostDto getPostById(long id) {
                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
                return modelToDto(post);
        }

        public PostDto updatePost(PostDto postDto, long id) {
                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

                Category category = categoryRepository.findById(postDto.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",
                                                postDto.getCategoryId()));

                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                post.setDescription(postDto.getDescription());
                post.setCategory(category);

                Post updatedPost = postRepository.save(post);
                return modelToDto(updatedPost);
        }

        public void deletePostById(long id) {
                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
                postRepository.delete(post);
        }

        public List<PostDto> getPostsByCategory(Long categoryId) {
                categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

                List<Post> postList = postRepository.findByCategoryId(categoryId);
                return postList.stream()
                                .map((post) -> modelToDto(post))
                                .collect(Collectors.toList());
        }
}
