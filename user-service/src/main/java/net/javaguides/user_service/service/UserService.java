package net.javaguides.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.javaguides.user_service.dto.DepartmentDto;
import net.javaguides.user_service.dto.ResponseDto;
import net.javaguides.user_service.dto.UserDto;
import net.javaguides.user_service.entity.User;
import net.javaguides.user_service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public ResponseDto getUserById(Long userId) {

        User user = userRepository.findById(userId).get();

        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(
            "http://localhost:8080/api/departments/" + user.getDepartmentId(),
            DepartmentDto.class
        );
        DepartmentDto department = responseEntity.getBody();

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        ResponseDto responseDto = new ResponseDto();
        responseDto.setUser(userDto);
        responseDto.setDepartment(department);

        return responseDto;
    }
}