package com.tcdt.qlnvchatluong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcdt.qlnvchatluong.jwt.CustomUserDetails;
import com.tcdt.qlnvchatluong.repository.UserActionRepository;
import com.tcdt.qlnvchatluong.repository.UserHistoryRepository;
import com.tcdt.qlnvchatluong.repository.UserInfoRepository;
import com.tcdt.qlnvchatluong.table.UserAction;
import com.tcdt.qlnvchatluong.table.UserHistory;
import com.tcdt.qlnvchatluong.table.UserInfo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserInfoRepository userRepository;
	@Autowired
	UserHistoryRepository userHistoryRepository;

	@Autowired
	UserActionRepository userActionRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}

	public Iterable<UserAction> findAll() {
		return userActionRepository.findAll();
	}

	public void saveUserHistory(UserHistory userHistory) {
		userHistoryRepository.save(userHistory);
	}

}