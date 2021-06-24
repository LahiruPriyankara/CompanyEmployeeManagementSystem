package com.company.service.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.service.models.CompanyDepartment;
import com.company.service.models.CompanyUser;
import com.company.service.models.NonCompanyUser;
import com.company.service.repository.CompanyDepartmentRepository;
import com.company.service.repository.CompanyUserRepository;
import com.company.service.repository.NonCompanyUserRepository;
import com.company.service.util.AppConstants;

@RestController
@RequestMapping(value = "/company")
public class MainController {

	@Autowired
	NonCompanyUserRepository nonCompanyUserRepository;
	@Autowired
	CompanyUserRepository companyUserRepository;
	@Autowired
	CompanyDepartmentRepository companyDepartmentRepository;

	@GetMapping("/test")
	public String test() {
		try {
			System.out.println("MainController.test()");
		} catch (Exception e) {
		}
		return "Company Web Serice is called.......";
	}

	// ---------------------- DEPARTMENT RELATED -----------------------------------START
	@GetMapping("/allDepartments")
	public List<CompanyDepartment> getDepartments() {
		try {
			System.out.println("MainController.getDepartments()");
			return companyDepartmentRepository.findAll();
		} catch (Exception e) {
			System.out.println("Error in MainController.getDepartments() : " + e.getMessage());
		}
		return new ArrayList<>();
	}
	// ---------------------- DEPARTMENT RELATED -----------------------------------END

	// ---------------------- NON COMPANY USER -------------------------------------START
	@GetMapping("/allNonCompanyUsers")
	public List<NonCompanyUser> getAllNonCompanyUsers() {
		try {
			System.out.println("MainController.getAllNonCompanyUsers()");
			return nonCompanyUserRepository.findAll();
		} catch (Exception e) {
			System.out.println("Error in MainController.getAllNonCompanyUsers() : " + e.getMessage());
		}
		return new ArrayList<>();
	}

	@GetMapping("/createNonCompanyUser/{userId}")
	public String createNonCompanyUser(@PathVariable("userId") String userId) {
		NonCompanyUser user;
		String createStatus = AppConstants.USER_CREATION_ERROR;
		List<NonCompanyUser> userList = null;
		try {
			System.out.println("MainController.createNonCompanyUser()");
			System.out.println("userId " + userId);
			
			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() > 0)
				return AppConstants.USER_ALREADY_EXISTING;

			user = new NonCompanyUser();
			user.setUser_id(userId);
			user.setUser_pswd("");
			user.setStatus(AppConstants.USER_STATUS_CREATE);
			nonCompanyUserRepository.save(user);
			createStatus = AppConstants.USER_STATUS_CREATE;
		} catch (Exception e) {
			System.out.println("Error in MainController.createNonCompanyUser() : " + e.getMessage());
		}
		return createStatus;
	}
	
	@GetMapping("/verifyNonCompanyUser/{userId}")
	public boolean createVerifyNonCompanyUser(@PathVariable("userId") String userId) {
		boolean isSuccess = false;
		NonCompanyUser user;
		List<NonCompanyUser> userList = null;
		try {
			System.out.println("MainController.verifyNonCompanyUser()");
			System.out.println("userId " + userId);
			
			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() == 0)
				return false;

			user = userList.get(0);
			user.setStatus(AppConstants.USER_CREATE_VERIFY);
			nonCompanyUserRepository.save(user);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error in MainController.verifyNonCompanyUser() : " + e.getMessage());
		}
		return isSuccess;
	}

	@GetMapping("/setPasswordNonCompanyUser/{userId}/{newPassword}")
	public boolean setPasswordNonCompanyUser(@PathVariable("userId") String userId,@PathVariable("newPassword") String newPassword) {
		boolean isSuccess = false;
		List<NonCompanyUser> userList = null;
		NonCompanyUser user;
		try {
			System.out.println("MainController.resetPasswordNonCompanyUser()");
			System.out.println("userId " + userId + " | password " + newPassword);

			if (newPassword == null || newPassword.isEmpty()) {
				System.out.println("Empty values recived.");
				return false;
			}

			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() == 0)
				return false;

			user = userList.get(0);
			user.setUser_pswd(newPassword);
			user.setLoggin_fail_count(0);
			user.setStatus(AppConstants.USER_SET_PASSWORD);

			nonCompanyUserRepository.save(user);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error in MainController.resetPasswordNonCompanyUser() : " + e.getMessage());
		}
		return isSuccess;
	}
	
	@GetMapping("/activePasswordNonCompanyUser/{userId}")
	public boolean activePasswordNonCompanyUser(@PathVariable("userId") String userId) {
		boolean isSuccess = false;
		List<NonCompanyUser> userList = null;
		NonCompanyUser user;
		try {
			System.out.println("MainController.activePasswordNonCompanyUser()");
			System.out.println("userId " + userId);

			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() == 0)
				return false;

			user = userList.get(0);
			user.setStatus(AppConstants.USER_STATUS_ACTIVE);

			nonCompanyUserRepository.save(user);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error in MainController.activePasswordNonCompanyUser() : " + e.getMessage());
		}
		return isSuccess;
	}
	
	@GetMapping("/resetPasswordNonCompanyUser/{userId}/{newPassword}")
	public boolean resetPasswordNonCompanyUser(@PathVariable("userId") String userId,@PathVariable("newPassword") String newPassword) {
		boolean isSuccess = false;
		List<NonCompanyUser> userList = null;
		NonCompanyUser user;
		try {
			System.out.println("MainController.resetPasswordNonCompanyUser()");
			System.out.println("userId " + userId + " | password " + newPassword);

			if (newPassword == null || newPassword.isEmpty()) {
				System.out.println("Empty values recived.");
				return false;
			}

			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() == 0)
				return false;

			user = userList.get(0);
			user.setUser_pswd(newPassword);
			user.setLoggin_fail_count(0);
			user.setStatus(AppConstants.USER_STATUS_ACTIVE);

			nonCompanyUserRepository.save(user);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error in MainController.resetPasswordNonCompanyUser() : " + e.getMessage());
		}
		return isSuccess;
	}
	
	@GetMapping("/unlockPasswordNonCompanyUser/{userId}")
	public boolean unlockPasswordNonCompanyUser(@PathVariable("userId") String userId) {
		boolean isSuccess = false;
		List<NonCompanyUser> userList = null;
		NonCompanyUser user;
		try {
			System.out.println("MainController.unlockPasswordNonCompanyUser()");
			System.out.println("userId " + userId);
			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() == 0)
				return false;

			user = userList.get(0);
			user.setStatus(AppConstants.USER_STATUS_UNLOCKPASSWORD);

			nonCompanyUserRepository.save(user);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error in MainController.unlockPasswordNonCompanyUser() : " + e.getMessage());
		}
		return isSuccess;
	}
	
/*
	@GetMapping("/resetPasswordNonCompanyUser/{userId}/{newPassword}")
	public boolean resetPasswordNonCompanyUser(@PathVariable("userId") String userId,@PathVariable("newPassword") String newPassword) {
		boolean isSuccess = false;
		List<NonCompanyUser> userList = null;
		NonCompanyUser user;
		try {
			System.out.println("MainController.resetPasswordNonCompanyUser()");
			System.out.println("userId " + userId + " | password " + newPassword);

			if (newPassword == null || newPassword.isEmpty()) {
				System.out.println("Empty values recived.");
				return false;
			}

			userList = nonCompanyUserRepository.findByUser_id(userId);
			if (userList.size() == 0)
				return false;

			user = userList.get(0);
			user.setUser_pswd(newPassword);
			user.setLoggin_fail_count(0);
			user.setStatus(AppConstants.USER_STATUS_ACTIVE);

			nonCompanyUserRepository.save(user);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error in MainController.resetPasswordNonCompanyUser() : " + e.getMessage());
		}
		return isSuccess;
	}
*/
	@GetMapping("/loginNonCompanyUser/{userId}/{password}")
	public String loginNonCompanyUser(@PathVariable("userId") String userId,@PathVariable("password") String password) {
		List<NonCompanyUser> userList = null;
		NonCompanyUser user;
		try {
			System.out.println("MainController.loginNonCompanyUser()");
			System.out.println("userId " + userId + " | password " + password);
			userList = nonCompanyUserRepository.findByUseridPaswd(userId, password);
			System.out.println("userList.size()  " +userList.size());
			if (userList.size() > 0) {
				user = userList.get(0);
				if (user.getStatus().equalsIgnoreCase(AppConstants.USER_STATUS_LOCKPASSWORD)) {
					return AppConstants.LOCK_USER;
				}

				if (user.getStatus().equalsIgnoreCase(AppConstants.USER_STATUS_UNLOCKPASSWORD)) {
					return AppConstants.SET_NEW_PASSWORD;
				}

				return AppConstants.LOGIN_SUCCESS;
			} else {
				userList = nonCompanyUserRepository.findByUser_id(userId);
				if (userList.size() == 0) {
					return AppConstants.LOGIN_FAIL;
				}

				user = userList.get(0);
				if (user.getStatus().equalsIgnoreCase(AppConstants.USER_STATUS_LOCKPASSWORD)) {
					return AppConstants.LOCK_USER;
				}
				if (user.getStatus().equalsIgnoreCase(AppConstants.USER_STATUS_UNLOCKPASSWORD)) {
					return AppConstants.SET_NEW_PASSWORD;
				}

				int count = user.getLoggin_fail_count();
				if (count > 4) {
					user.setStatus(AppConstants.USER_STATUS_LOCKPASSWORD);
				} else {
					user.setLoggin_fail_count(count + 1);
				}

				nonCompanyUserRepository.save(user);
				return AppConstants.LOGIN_FAIL;
			}
		} catch (Exception e) {
			System.out.println("Error in MainController.loginNonCompanyUser() : " + e.getMessage());
			return AppConstants.SERVER_ERROR;
		}
	}

	// ---------------------- NON COMPANY USER -------------------------------------END

	// -------------------------- COMPANY USER -------------------------------------START

	@GetMapping("/allCompanyUsers")
	public List<CompanyUser> getAllCompanyUsers() {
		List<CompanyUser> userList = null;
		try {
			System.out.println("MainController.getAllCompanyUsers()");
			userList = companyUserRepository.findAll();
			System.out.println("userList.size()  " +userList.size());
			return userList;
		} catch (Exception e) {
			System.out.println("Error in MainController.getAllCompanyUsers() : " + e.getMessage());
			return new ArrayList<>();
		}
	}

	@GetMapping("/companyUsersByDepId/{depId}")
	public List<CompanyUser> getCompanyUsersByDepId(@PathVariable("depId") String depId) {
		try {
			System.out.println("MainController.getCompanyUsersByDepId()");
			System.out.println("depId " + depId);
			return companyUserRepository.findByUserDepId(depId);
		} catch (Exception e) {
			System.out.println("Error in MainController.getCompanyUsersByDepId() : " + e.getMessage());
			return new ArrayList<>();
		}
	}

	@GetMapping("/companyUsersByUserId/{userId}")
	public CompanyUser getCompanyUsersByUserId(@PathVariable("userId") String userId) {
		List<CompanyUser> list; 
		try {
			System.out.println("MainController.getCompanyUsersByUserId()");
			System.out.println("userId " + userId);
			list = companyUserRepository.findByUser_id(userId);
			
			if(list.size() == 0) {return new CompanyUser();}
			
			return list.get(0);
		} catch (Exception e) {
			System.out.println("Error in MainController.getCompanyUsersByUserId() : " + e.getMessage());
			return new CompanyUser();
		}
	}
	
	@GetMapping("/createCompanyUsersByUserRole/{userId}/{roleId}")
	public boolean createCompanyUsersByUserRole(@PathVariable("userId") String userId,@PathVariable("roleId") int roleId) {
		List<CompanyUser> list; 
		CompanyUser user; 
		try {
			System.out.println("MainController.createCompanyUsersByUserRole()");
			System.out.println("userId " + userId+" roleId "+roleId);
			list = companyUserRepository.findByUser_id(userId);			
			if(list.size() == 0) {return false;}
			user = list.get(0);
			user.setUserRole(roleId);
			companyUserRepository.save(user);
			return true;
		} catch (Exception e) {
			System.out.println("Error in MainController.createCompanyUsersByUserRole() : " + e.getMessage());
			return false;
		}
	}

	@GetMapping("/loginCompanyUser/{userId}/{password}")
	public String loginCompanyUser(@PathVariable("userId") String userId, @PathVariable("password") String password) {
		try {
			System.out.println("MainController.loginCompanyUser()");
			System.out.println("userId " + userId + " | password " + password);
			List<CompanyUser> userList = companyUserRepository.findByUseridPaswd(userId, password);
			System.out.println("userList.size()  " +userList.size());
			if (userList.size() == 0) {
				return AppConstants.LOGIN_FAIL;
			}
			return AppConstants.LOGIN_SUCCESS;
		} catch (Exception e) {
			System.out.println("Error in MainController.loginCompanyUser() : " + e.getMessage());
			return "Server Error.Please Try Again Later.";
		}
	}
	
	@GetMapping("/companyUsersAssignCommonAdminAuthRole/{userId}/{role}")
	public boolean getCompanyUsersByUserId(@PathVariable("userId") String userId,@PathVariable("role") int role) {
		List<CompanyUser> list; 
		CompanyUser user;
		boolean isSuccess = false;
		try {
			System.out.println("MainController.getCompanyUsersByUserId()");
			System.out.println("userId " + userId+" , role "+role);
			list = companyUserRepository.findByUser_id(userId);
			if (list.size() == 0)
				return false;

			user = list.get(0);
			user.setUserRole(role);

			companyUserRepository.save(user);
			isSuccess = true;
			
		} catch (Exception e) {
			System.out.println("Error in MainController.getCompanyUsersByUserId() : " + e.getMessage());
		}
		return isSuccess;
	}

	// ------------------------- COMPANY USER -------------------------------------END
}
