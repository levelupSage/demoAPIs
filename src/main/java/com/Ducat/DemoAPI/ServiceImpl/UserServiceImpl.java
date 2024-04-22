package com.Ducat.DemoAPI.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ducat.DemoAPI.Entity.User;
import com.Ducat.DemoAPI.Repositories.UserRepo;
import com.Ducat.DemoAPI.Service.UserService;
import com.Ducat.DemoAPI.exception.ResourceNotFoundException;
import com.Ducat.DemoAPI.payLoads.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User saveUser = this.userRepo.save(user);
		return this.usertoUserDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		 User user = this.userRepo.findById(userId)
				 .orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(user.getPassword());user.setAbout(userDto.getAbout());
		User updateUser= this.userRepo.save(user);
		UserDto updateUserDto = this.usertoUserDto(updateUser);
		return updateUserDto;
	}
 
	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.usertoUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>	users = this.userRepo.findAll();
		List<UserDto> userDto = users.stream().map(user -> this.usertoUserDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	private UserDto usertoUserDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}
}
