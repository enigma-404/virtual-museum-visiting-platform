package org.enigma.virtualmuseumvisitingplatform.service.abstracts;

import org.enigma.virtualmuseumvisitingplatform.core.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserInfoResponse;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserSignupDto;

public interface IUserService {
    DataResult<UserInfoResponse> login(UserLoginDto userLoginDto);
    Result register(UserSignupDto userSignupDto);
}
