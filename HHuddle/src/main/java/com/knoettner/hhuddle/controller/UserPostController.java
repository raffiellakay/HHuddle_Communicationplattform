package com.knoettner.hhuddle.controller;

import com.knoettner.hhuddle.Category;
import com.knoettner.hhuddle.dto.PostDto;
import com.knoettner.hhuddle.service.UserPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {
    @Autowired
    private UserPostService userPostService;

    @PostMapping("/post")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto> createUserPost(@RequestBody PostDto postDto) {
        PostDto createdPost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PostMapping("/blackboardpost")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto>createBlackboardPost(@RequestBody PostDto postDto) {
        postDto.setCategory(Category.BLACKBOARD.toString());
        PostDto createdBlackboardPost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlackboardPost);
    }

    @PostMapping("/packagepost")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto>createPackagePost(@RequestBody PostDto postDto) {
        postDto.setCategory(Category.PACKAGE.toString());
        PostDto createdPackagePost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackagePost);
    }

    @PostMapping("/exchangepost")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto>createExchangePost(@RequestBody PostDto postDto) {
        postDto.setCategory(Category.EXCHANGE.toString());
        PostDto createdExchangePost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExchangePost);
    }


    @PostMapping("/eventspost")
   @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto>createEventsPost(@RequestBody PostDto postDto) {
        postDto.setCategory(Category.EVENTS.toString());
        PostDto createdEventsPost = userPostService.createUserPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventsPost);
    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('RESIDENT')")
    @Operation(summary = "Upload an image", description = "Uploads an image file and returns the file path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully",
                    content = @Content(schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Invalid file"),
            @ApiResponse(responseCode = "500", description = "Error while saving the image")
    })
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "Image file to upload", required = true)
            @RequestParam("file") MultipartFile file) {


        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Fehler: Kein Bild hochgeladen!");
        }

        // Define the directory
        String folderPath = "./files";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Generate a unique file name
        String fileName = "photo_" + LocalDateTime.now().toString().replace(":", "-") + ".jpg";
        String filePath = folderPath + File.separator + fileName;

        // Save the file
        try (OutputStream out = new FileOutputStream(filePath)) {
            out.write(file.getBytes());
            out.flush();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Speichern des Bildes: " + e.getMessage());
        }

        return ResponseEntity.ok(filePath);
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto> updateUserPost(@RequestBody PostDto postDto) {
        PostDto updatedPost = userPostService.updateUserPost(postDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);

    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/{id}")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<PostDto>  getPost(@PathVariable("id") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(userPostService.getPost(postId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/allposts")
   @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity  <Set<PostDto>> getAllPosts(){
        Set<PostDto> allPosts = userPostService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<Set<PostDto>> getPostsByUserId(@PathVariable("id") Long userId){
        Set<PostDto>userPosts = userPostService.getPostsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userPosts);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/post/{id}")
   @PreAuthorize("hasRole('RESIDENT')")
    void deletePost(@PathVariable("id") Long postId) {
        userPostService.deletePost(postId);
    }

     @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('RESIDENT')")
    public ResponseEntity<Void> deletePostsByUserId(@PathVariable("userId") Long userId) {
        userPostService.deletePostsByUserId(userId);
        return ResponseEntity.noContent().build();// "No content" nur am Backend. der Server schickt keinen Body zurück!
        // return ResponseEntity.status(HttpStatus.OK).build();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}")
    @PreAuthorize("hasRole('RESIDENT')")
    public Set<PostDto> getPostsByHouseId(@PathVariable("id") Long houseId){
       return userPostService.getPostsByHouseId(houseId);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/house/{id}/{category}")
    @PreAuthorize("hasRole('RESIDENT')")
   public Long getBoardIdByHouseIdAndCategory(@PathVariable("id") Long houseId, @PathVariable("category") String category) {
        return userPostService.getBoardIdByHouseIdAndCategory(houseId, category);
    }
}

