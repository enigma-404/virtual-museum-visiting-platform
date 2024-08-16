package org.enigma.virtualmuseumvisitingplatform.service.abstracts.users;

import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserInfoResponse;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserSignupDto;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;

import java.util.List;

public interface IUserService {
    DataResult<UserInfoResponse> login(UserLoginDto userLoginDto);
    Result register(UserSignupDto userSignupDto);
    Result addFavourite(long userId, long artifactId);
    DataResult<List<Artifact>> getFavourites(long userId);
}
