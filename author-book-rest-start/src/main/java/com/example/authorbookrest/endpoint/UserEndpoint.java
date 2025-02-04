package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.AuthRequestDto;
import com.example.authorbookrest.dto.AuthResponseDto;
import com.example.authorbookrest.dto.CreateUserRequestDto;
import com.example.authorbookrest.dto.UserDto;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.mapper.UserMapper;
import com.example.authorbookrest.service.UserService;
import com.example.authorbookrest.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserEndpoint {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${upload.image.path}")
    private String uploadImagePath;


    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequest) {
        User byEmail = userService.findByEmail(createUserRequest.getEmail());
        if (byEmail != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(userService.create(createUserRequest));
    }

    @PostMapping(value = "/image/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserDto> uploadImage(@PathVariable("id") int userId,
                                               @RequestPart(value = "picture", required = false) MultipartFile multipartFile) throws IOException {
        User byId = userService.findById(userId);
        if (byId == null) {
            ResponseEntity.notFound().build();
        }
        userService.uploadImage(byId, multipartFile);
        return ResponseEntity.ok(userMapper.map(byId));
    }

    @GetMapping(value = "getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    @SneakyThrows
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) {
        File file = new File(uploadImagePath, picName);
        if (file.exists()) {
            return IOUtils.toByteArray(new FileInputStream(file));
        }
        return null;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        User user = userService.findByEmail(authRequestDto.getEmail());
        if (user == null || !passwordEncoder.matches(authRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = userMapper.map(user);
        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(jwtTokenUtil.generateToken(user.getEmail()))
                .userDto(userDto)
                .build());
    }

    @GetMapping("/profile/{id}")
    public UserDto getProfile(@PathVariable("id") int userId) {
        User user = userService.findById(userId);
        return userMapper.map(user);
    }

}
